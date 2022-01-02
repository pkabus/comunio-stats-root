#!/bin/bash
# make networking define a static ip in dhcp client deamon /etc/dhcpcd.conf
echo '# define static ip addresses
interface wlan0
static ip_address=192.168.178.106/24
static routers=192.168.178.1
static domain_name_servers=192.168.178.1

interface eth0
static ip_address=192.168.178.105/24
static routers=192.168.178.1
static domain_name_servers=192.168.178.1' >> /etc/dhcpcd.conf

# update and upgrade
sudo apt-get update
sudo apt-get upgrade

# create image from remote (tested)
# ssh pi@192.168.178.105 "sudo -S dd if=/dev/mmcblk0 | gzip -1 -" | dd of=rpi4.gz
# or locally (not tested)
# dd if=/dev/mmcblk0 | gzip -1 - | dd of=rpi_image.gz

# install virtualenv, scrapy dependencies and scrapy
sudo pip install virtualenv
virtualenv scrapyenv
# activate venv (you need a script like activate_scrapyenv.sh)
# source scrapyenv/bin/activate

#### inside scrapyenv ####
# sudo apt-get update
# sudo apt-get upgrade
#
# sudo apt-get install libffi-dev libxml2-dev libxslt1-dev libssl-dev python-dev
# sudo pip install scrapy
#### inside scrapyenv ####


# create git and transfer directories
mkdir ~/projects
mkdir ~/transfer
mkdir ~/transfer/scrapy

# clone git comunio-stats repo
ssh-keygen -t ed25519 -C "peter.kabus@gmx.de"
cat ~/.ssh/id_ed25519.pub
# add that public key to github account
git clone git@github.com:pkabus/comunio-stats.git

# copy scrapy project from git repo to transfer/scrapy
cp -r ~/projects/comunio-stats/ ~/transfer/scrapy/

# activate virtualenv
# source ~/scrapyenv/bin/activate

# go to scrapy dir and run spider
cd transfer/scrapy/comunio-stats/scripts
# ./run.sh

# install mail transfer agent (MTA), but not necessary!
# sudo apt-get install postfix
