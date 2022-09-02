from flask import Flask, render_template, request, url_for, redirect, flash
import torch
import logging
import time
import numpy as np
import cv2
import json
import base64

from inference import *

app = Flask(__name__)


ALLOWED_EXTENSIONS = set(['nii', 'dcm', 'jpg', 'jpeg', 'png'])

@app.route('/')
def home():
    return render_template("home.html")


@app.route('/fetchImage', methods = ['POST', 'GET'])
def FetchImage():


    outputImageName = ""
    inputImageName = ""
    response = []
    counter = 0

    if request.method == 'POST':
        
        # Read image files in list
        files = request.files.getlist("files")

        
        # Check file part & file type
        for idx, file in enumerate(files):


            if file.filename == '':
                flash(f"No {idx+1}th file part")

            
            if not allowed_file(file.filename, ALLOWED_EXTENSIONS):
                flash("Not allowed extension")
        # Save nii files, the other files will be saved in other sections
            if 'nii' in file.filename:
                file.save("./static/" + file.filename)
            
        # Input single dcm,jpeg,png image OR a pack of niis 
            processedImg = ct_slices_generator(file.filename, file)
        # Initialize the model    
            model = GetModel()
        # Inference
            tumorSize, tumorExistence = infer(file.filename, processedImg, model)


            CHECK = True
            if 'nii' in file.filename:
                outputImageName = "./static/{}_output/{}.jpg".format(file.filename, idx)
                inputImageName = "./static/{}_input/{}.jpg".format(file.filename, idx)
                # response.append(returnImage(inputImageName, outputImageName, tumorSize, tumorExistence))
                return returnImage(inputImageName, outputImageName, tumorSize, tumorExistence)
                
            else:
                outputImageName = "./static/others_output/{}.jpg".format(file.filename)
                inputImageName = "./static/others_input/{}.jpg".format(file.filename)
                # response.append(returnImage(inputImageName, outputImageName, tumorSize, tumorExistence))
                return returnImage(inputImageName, outputImageName, tumorSize, tumorExistence)


    # return response

                               
def allowed_file(filename, ALLOWED_EXTENSIONS):
    extension = filename.split('.')[1]
    if extension in ALLOWED_EXTENSIONS:
        return True
    return False

def returnImage(inputImagePath, outputImagePath, tumorSize, tumorExistence):
    image1 = cv2.imread(inputImagePath)
    image2 = cv2.imread(outputImagePath)
    encodedImage1 = cv2.imencode('.jpg', image1)[1]
    encodedImage2 = cv2.imencode('.jpg', image2)[1]
    base64_enocded1 = base64.b64encode(encodedImage1)
    base64_enocded2 = base64.b64encode(encodedImage2)
    # print("base64: {} ".format(base64_enocded1[:50]))
    # print()
    return {'INPUT': base64_enocded1.decode('utf-8'), 'OUTPUT': base64_enocded2.decode('utf-8'), 'TUMOR_SIZE': tumorSize, 'TUMOR_EXISTENCE': tumorExistence}


def GetModel():
    model = torch.load('./model.pth')
    return model


if __name__ == '__main__':
    # app.secret_key = 'super secret key'
    # app.config['SESSION_TYPE'] = 'filesystem'

    app.run(debug=True, host='0.0.0.0', port=8080)
    logging.basicConfig()

