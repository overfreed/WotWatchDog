package com.example.myapplication;

import java.util.List;

class ReserveWot{
        public String name;
        public String bonus_type;
        public Boolean disposable;
        public List<InStock>[] inStocks;
        public String  type;
        public String  icon;
        public ReserveWot(String name,String bonus_type,Boolean disposable, String type,String icon){
            this.name=name;
            this.bonus_type=bonus_type;
            this.disposable=disposable;
            this.type=type;
            this.icon=icon;
        }
    }


    class InStock{
        public String status;
        public int action_time;
        public String active_till;
        public int level;
        public String activated_at;
        public int amount;
        public List<BonusValue> bonusValues;
        public Boolean x_level_only;

        public  InStock( String status,int action_time,String active_till,int level,String activated_at,int amount,Boolean x_level_only){
            this.status=status;
            this.action_time=action_time;
            this.active_till=active_till;
            this.level=level;
            this.activated_at=activated_at;
            this.amount=amount;
            this.x_level_only=x_level_only;
        };
    }

    class BonusValue{
        public float value;
        public String battleType;
        public BonusValue(float value, String battleType){
            this.value=value;
            this.battleType=battleType;
        };
    };




