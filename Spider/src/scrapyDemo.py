#-*_coding:utf-8*-
import requests 
import string
from lxml import etree
from cgitb import text
header = {"User-Agent":"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2383.0 Safari/537.36"}
 
def getArtistUrls():
    u = [] 
    for i in string.ascii_lowercase:
        url = "http://www.kuwo.cn/geci/artist_"+i+".htm"
        html = requests.get(url,params = header)
        selector = etree.HTML(html.text)
        a = selector.xpath('//*[@id="pageDiv"]/*')
        for j in range(1,len(a)):
            u.append('http://www.kuwo.cn/geci/artist_'+i+'_'+str(j)+'.htm')       
    return u
   
          
def getAllArtist():
    urls = getArtistUrls()
    for url in urls:
        html = requests.get(url,params=header)
        selector = etree.HTML(html.text)
        list_art = selector.xpath('//ul[@class="songer_list"]/*')
        for a in list_art:
            print a.xpath('@href').text # ///html/body/div[7]/div[1]/ul/li[1]/a
            
     
getAllArtist() 
    