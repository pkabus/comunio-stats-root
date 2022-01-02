#!/bin/bash

###### ######
# Miniconda and python 3.6 MANDATORY
##### ######
# reference stackoverflow conda@raspberrypi installation
# install miniconda at /home/pi/miniconda3
# https://stackoverflow.com/questions/39371772/how-to-install-anaconda-on-raspberry-pi-3-model-b

export PATH="/home/pi/miniconda3/bin:$PATH"
# Functions are not exported by default to be made available in subshells

# conda/scrapy variables
VENV=env-comunio
BASE_DIR=/home/pi/transfer/scrapy
SCRAPY_PRJ=comunio_stats
VENV_CREATED=$(conda env list | grep $VENV)


if [ -z "$VENV_CREATED" ]
then
echo "virtual environment $VENV does not exist, so I create it..."
conda create -n $VENV python=3.6
source activate $VENV
conda install anaconda-client
pip install --upgrade pip
pip install scrapy
else
# simply activate already existing conda venv
echo "Activate virtual environment"
source activate $VENV
fi

# cd to scrapy project folder where scrapy.cfg is located
cd $BASE_DIR/$SCRAPY_PRJ

# Run crawler
timestamp=$(date +%s)
echo "Run crawler..."
scrapy crawl crawl-clubs -o ${timestamp}_bundesliga_player.json

# deactivate conda venv
echo "Deactivate virtual environment"
source deactivate
