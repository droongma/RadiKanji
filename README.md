# RadiKanji

## Introduction
**RadiKanji is an efficient Japanese kanji(한자) Learning app with Radicals(부수).**

It is made with MVP design pattern, AWS Lambda(with Flask backend code) and RDS(PostgreSQL DB) services.

## Flask Codes to implement AWS Lambda

Creating Radical info table in RDS : <https://gist.github.com/droongma/53bae488ddb0414026466f06c36b3a07>

Creating Kanji info table in RDS : <https://gist.github.com/droongma/e642ac882e0974999cbe476d176c35fc>

Kanji and Radical data stored in these two RDS tables are from [Kanji Alive Github.](https://github.com/kanjialive/kanji-data-media)

Flask Code to get Radical list from RDS Radical table : [get_radicals.py](https://gist.github.com/droongma/973dbba9099bf415b02357456fe053a3)

Flask Code to get Kanji list from RDS Kanji table : [get_kanjis.py](https://gist.github.com/droongma/4f53a64b5a56a36304a84b0988740118)

## Features:

1. Provides Kanji and Radical lists categorized by stroke number(획수)

2. If you click each Kanji, you can see detailed Kanji information(e.g. radical info, example sentences, meaning, onyomi(음독) and kunyomi(훈독))

3. You can bookmark as many Kanjis as you want
<br/><br/>

## Demonstration
**Final demonstration video is in this URL:**
https://vimeo.com/728032002

## Execution Flow
![image](https://user-images.githubusercontent.com/11453455/177934446-e4530ec5-3e34-4004-817f-5564260439db.png)

## Screenshots
![image](https://user-images.githubusercontent.com/11453455/177940225-78b9e7ab-da03-45b4-96b6-09834493b934.png)
![image](https://user-images.githubusercontent.com/11453455/177940381-1777a050-fce8-401b-94c2-3df4afa20c12.png)
![image](https://user-images.githubusercontent.com/11453455/177940426-fec4f149-311b-4083-b488-e87be0f06f1d.png)
![image](https://user-images.githubusercontent.com/11453455/177940460-e98a60e7-ef3a-4e98-873e-cc78aefb2178.png)

## References
1.	kanjiapi.dev 

    (a)	License : MIT License(Specified in kanjiapi.dev Github) 
    
    (b)	Documentation URL : https://kanjiapi.dev/#!/documentation

2.	Kanji Alive API

    (a)	License : Creative Commons Attribution (CC-BY) License
    
    (b)	Documentation URL : https://rapidapi.com/KanjiAlive/api/learn-to-read-and-write-japanese-kanji/

Note : Offline Japanese Radical(한자 부수) files (including font and images), and Kanji(일본어 한자) files used or modified in this application are from Kanji Alive Github, which can also be accessed via Kanji Alive API website. These files all have CC-BY License. 
