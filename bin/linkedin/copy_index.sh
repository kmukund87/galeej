#!/bin/bash
ssh lor1-app5697.prod.linkedin.com  # a desired Seas searcher
 
sudo locker run seas-peoplesearch-searcher_i000
ls -l /export/content/data/seas/seas-peoplesearch-searcher/i000/index/
 
lrwxrwxrwx 1 app app 172 Jan 13 23:47 base -> /export/content/data/seas/seas-peoplesearch-searcher/i000/cached-data-mover/prod_0.3.1184_2020-01-07-03_galene-peoplesearch-p32v1_5c23d12b40c169d9bfd8560bbea678e1/i000/base
lrwxrwxrwx 1 app app 182 Jan 13 23:47 middle-3686336050 -> /export/content/data/seas/seas-peoplesearch-searcher/i000/cached-data-mover/prod_0.3.1184_2020-01-07-03_galene-peoplesearch-p32v1_5c23d12b40c169d9bfd8560bbea678e1/snapshot_3686336050
 
exit
 
sudo locker cp seas-peoplesearch-searcher_i000 /export/content/data/seas/seas-peoplesearch-searcher/i000/cached-data-mover/prod_0.3.1184_2020-01-07-03_galene-peoplesearch-p32v1_5c23d12b40c169d9bfd8560bbea678e1/i000/base . --out
 
sudo locker cp seas-peoplesearch-searcher_i000 /export/content/data/seas/seas-peoplesearch-searcher/i000/cached-data-mover/prod_0.3.1184_2020-01-07-03_galene-peoplesearch-p32v1_5c23d12b40c169d9bfd8560bbea678e1/snapshot_3686336050 middle-3686336050 --out
