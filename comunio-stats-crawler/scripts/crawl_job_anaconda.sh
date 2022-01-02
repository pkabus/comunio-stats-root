#!/bin/bash

# Prerequirements
# Anaconda must already be installed

# USAGE
# Run 'y | ./crawl_job.sh' to automatically create the virtual environment (if not yet created)
# otherwise './crawl_job-sh' is sufficient

# Functions are not exported by default to be made available in subshells
source ~/anaconda3/etc/profile.d/conda.sh

# conda venv name
VENV=env-comunio

VENV_CREATED=$(conda env list | grep $VENV)

if [ -z "$VENV_CREATED" ]
then
echo "virtual environment $VENV does not exist, so I create it..."
conda create -n $VENV
conda activate $VENV
conda install -c conda-forge scrapy
else
# simply activate already existing conda venv
conda activate $VENV
fi


timestamp=$(date +%s)

# run crawler
scrapy crawl crawl-clubs -o ${timestamp}_bundesliga_player.json

# deactivate conda venv
conda deactivate
