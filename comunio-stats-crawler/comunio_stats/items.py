# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

from scrapy import Item, Field


class ComunioStatsItem(Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    pass

class ClubItem(Item):
    name = Field()
    link = Field()
    created = Field()

class PlayerItem(Item):
    name = Field()
    id = Field()
    link = Field()
    position = Field()
    market_value = Field()
    points_during_current_season = Field()
    club = Field()
    created = Field()