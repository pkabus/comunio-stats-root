# -*- coding: utf-8 -*-
import datetime

import scrapy
from scrapy.shell import inspect_response
from scrapy.linkextractors import LinkExtractor

from comunio_stats.items import ClubItem


class ListClubsSpider(scrapy.Spider):
    name = 'list-clubs'
    start_urls = ['https://stats.comunio.de/squad']

    def parse(self, response, **kwargs):
        now = datetime.datetime.now()
        clubs = response.xpath("//td[contains(@class,'clubPics')]/a")
        items = ClubItem()

        for club in clubs:
            items['name'] = club.xpath(".//img/@title").extract()
            items['link'] = club.xpath("./@href").extract()
            items['created'] = now

            yield items
