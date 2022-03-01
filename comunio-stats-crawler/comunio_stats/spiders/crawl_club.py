import datetime
import re
import scrapy

from scrapy.spiders import CrawlSpider, Rule
from scrapy.shell import inspect_response
from scrapy.linkextractors import LinkExtractor

from comunio_stats.items import PlayerItem

class CrawlClubSpider(CrawlSpider):
    name = 'crawl-clubs'
    start_urls = ['https://stats.comunio.de/squad']

    rules = (
        Rule(LinkExtractor(allow=('/squad/[0-9]+')), callback='parse_club'),)


    def parse_club(self, response):
        now = datetime.datetime.now()
        # TODO club = response.xpath("")
        player_rows = response.xpath("//div[@id='content']/table[contains(@class, 'playersTable')]/tbody/tr")
        
        for row in player_rows:
            item = PlayerItem()
            item['name'] = row.xpath("./td/div/a[contains(@class, 'playerName')][1]/text()").extract()
            item['link'] = row.xpath("./td/div/a[contains(@class, 'playerName')][1]/@href").extract()
            item['id'] = re.search(r"[0-9]+", item['link'].__str__()).group()
            item['position'] = row.xpath("./td/img[contains(@class, 'pos')]/@title").extract()
            item['points_during_current_season'] = row.xpath(".//td[4]/text()").extract()
            item['club'] = response.xpath("//td[contains(@class,'clubPics')]/a//img[contains(@class, 'sel_border')]/@title").extract()
            item['market_value'] = row.xpath(".//td[5]/@data-value").extract()
            item['created'] = now
            yield item
            