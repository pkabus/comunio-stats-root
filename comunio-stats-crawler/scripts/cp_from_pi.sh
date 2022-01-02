#!/bin/bash

if  ping -c 1 192.168.178.105 &> /dev/null
then
  /usr/bin/scp -i /home/$USER/.ssh/id_rsa 'pi@192.168.178.105:/home/pi/transfer/scrapy/comunio-stats/*bundesliga_player.json' /home/$USER/backups/comunio-stats/
fi
