#!/bin/bash
# source ~/transfer/scrapy/comunio-stats/activate_scrapyenv.sh
DIR=/home/pi/transfer/scrapy/comunio-stats/scripts
PATH=$PATH:/usr/local/bin
export PATH

cd $DIR
source activate_scrapyenv.sh
scrapy crawl crawl-clubs -o $(date +%s)_bundesliga_player.json

deactivate
