import numpy as np
import pandas as pd

import matplotlib.pyplot as plt
# import seaborn as sns
import lightgbm as lgb
import xgboost as xgb
from sklearn.model_selection import KFold, StratifiedKFold
from sklearn.metrics import accuracy_score, roc_auc_score
from scipy.stats import norm, rankdata
import warnings
import gc
import os
import time
import sys
import datetime
import matplotlib.pyplot as plt
# import seaborn as sns
from sklearn.metrics import mean_squared_error
warnings.simplefilter(action='ignore', category=FutureWarning)
warnings.filterwarnings('ignore')
from sklearn import metrics

# plt.style.use('seaborn')
# sns.set(font_scale=1)
pd.set_option('display.max_columns', 500)


# test = pd.read_csv('Metro_testA/testA_submit_2019-01-29.csv')
test_28 = pd.read_csv('Metro_testA/testA_record_2019-01-28.csv')
test=pd.read_csv('_10mins/testsubmit.csv')
def tansfer_weekend(d):
    if d in ['2015-04-04','2015-04-05','2015-04-06','2015-04-11','2015-04-12','2015-04-18','2015-04-19','2015-04-25','2015-04-26']:
        return 1
    else:
        return 0

def transfer_week(d):
    if d in ['2015-04-04','2015-04-05','2015-04-06','2015-04-11','2015-04-12','2015-04-18','2015-04-19','2015-04-25','2015-04-26']:
        return 0
    else:
        return 1

def transfer_date(d):
    day=d[-2:]
    day=int(day)
    return day-1

def get_base_features(df_):
    df = df_.copy()

    # base time
    # df['day'] = df['time'].apply(lambda x: int(x[8:10]))
    df['week']=df['date'].apply(transfer_week)
    # df['week'] = pd.to_datetime(df['time']).dt.dayofweek + 1
    df['weekend'] = df['date'].apply(tansfer_weekend)
    df['hour'] = df['time'].apply(lambda x: int(x[3:5]))
    df['minute'] = df['time'].apply(lambda x: int(x[6:7] + '0'))

    # count,sum
    result = df.groupby(['stationid', 'week', 'weekend', 'date', 'hour', 'minute']).status.agg(
        ['count', 'sum']).reset_index()

    # nunique
    tmp = df.groupby(['stationid'])['cardnum'].nunique().reset_index(name='cardnums_station')
    result = result.merge(tmp, on=['stationid'], how='left')
    tmp = df.groupby(['stationid', 'hour'])['cardnum'].nunique().reset_index(name='cardnums_station_hour')
    result = result.merge(tmp, on=['stationid', 'hour'], how='left')
    tmp = df.groupby(['stationid', 'hour', 'minute'])['cardnum'].nunique().reset_index(name='cardnums_station_hout_minute')
    result = result.merge(tmp, on=['stationid', 'hour', 'minute'], how='left')

    # in,out
    result['inNums'] = result['sum']
    result['outNums'] = result['count'] - result['sum']

    result['day_since_first']=result['date'].apply(transfer_date)
    result.fillna(0, inplace=True)
    del result['sum'], result['count']

    return result

def transfer_datetoday(d):
    a=int(d[-2:])
    return a
    # return int(d[-1])

def transfer_realweek(d):
    if d in [4,5,6,11,12,18,19,25,26]:
        return 0
    else:
        return 1

def transfer_realweekend(d):
    if d in [4,5,6,11,12,18,19,25,26]:
        return 1
    else:
        return 0

def transfer_Timetoday(d):
    timelist=d.split(' ')
    if len(timelist[0]) == 8:
        a=int(timelist[0][-1])
        return a
    else:
        a=int(timelist[0][-2:])
        return a

def transfer_Timetohour(d):
    timelist = d.split(' ')
    if len(timelist[1]) == 7:
        a=int(timelist[1][0])
        return a
    else:
        a=int(timelist[1][0:2])
        return a

def transfer_Timetominute(d):
    timelist = d.split(' ')
    if len(timelist[1]) == 7:
        a=int(int(timelist[1][2])*10)
        return a
    else:
        a=int(int(timelist[1][3])*10)
        return a

test['date']=test['startTime'].apply(transfer_Timetoday)
test['hour']=test['startTime'].apply(transfer_Timetohour)
test['minute']=test['startTime'].apply(transfer_Timetominute)
# from datetime import datetime
# date = [datetime.strftime(x, '%Y%m%d') for x in list(pd.date_range('20150427', '20150430'))]
# for i in date:
#     bfn='_10mins/'+str(i)+'newnew.csv'
#     test_sh=pd.read_csv(bfn)
#     test_sh['date']=test_sh['date'].apply(transfer_datetoday)
#     test_sh['week']=test_sh['date'].apply(transfer_realweek)
#     test_sh['weekend'] = test_sh['date'].apply(transfer_realweekend)
#     test_sh.rename(columns={'cardnums_station_hout_minute':'cardnums_station_hour_minute'}, inplace = True)
#     test_sh.drop(['Unnamed: 0'], axis=1, inplace=True)
#     test_sh2=test_sh.copy()
#     test_sh2.drop(['cardnums_station', 'cardnums_station_hour','cardnums_station_hour_minute','day_since_first'], axis=1, inplace=True)
#     fn = str(i)+'new1.csv'
#     fn2=str(i)+'new2.csv'
#     path = os.path.join("_10mins", fn)
#     test_sh.to_csv(path)
#     path = os.path.join("_10mins", fn2)
#     test_sh2.to_csv(path)