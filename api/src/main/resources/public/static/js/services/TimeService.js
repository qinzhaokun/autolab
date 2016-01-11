/**
 * Created by lishuang on 2015/7/12.
 */

//定义一个转换器
/** * 对Date的扩展，将 Date 转化为指定格式的String * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q)
 可以用 1-2 个占位符 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) * eg: * (new
 Date()).pattern("yyyy-MM-dd hh:mm:ss.S")==> 2006-07-02 08:09:04.423
 * (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04
 * (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04
 * (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04
 * (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
 */
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    var week = {
        "0": "/u65e5",
        "1": "/u4e00",
        "2": "/u4e8c",
        "3": "/u4e09",
        "4": "/u56db",
        "5": "/u4e94",
        "6": "/u516d"
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    if (/(E+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f" : "/u5468") : "") + week[this.getDay() + ""]);
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};

function ISODateString(d) { 
    d = d.replace(" ","T");
    return d;
} 

Date.prototype.setISO8601 = function (string) {
    var regexp = "([0-9]{4})(-([0-9]{2})(-([0-9]{2})" +
        "(T([0-9]{2}):([0-9]{2})(:([0-9]{2})(\.([0-9]+))?)?" +
        "(Z|(([-+])([0-9]{2}):([0-9]{2})))?)?)?)?";
    if (string) {
        var d = string.match(new RegExp(regexp));
        var offset = 0;
        var date = new Date(d[1], 0, 1);


        if (d[3]) {
            date.setMonth(d[3] - 1);
        }
        if (d[5]) {
            date.setDate(d[5]);

        }
        if (d[7]) {
            date.setHours(d[7]);
        }
        if (d[8]) {
            date.setMinutes(d[8]);
        }
        if (d[10]) {
            date.setSeconds(d[10]);
        }
        if (d[12]) {
            date.setMilliseconds(Number("0." + d[12]) * 1000);
        }
        if (d[14]) {
            offset = (Number(d[16]) * 60) + Number(d[17]);
            offset *= ((d[15] == '-') ? 1 : -1);
        }
        offset -= date.getTimezoneOffset();
        time = (Number(date) + (offset * 60 * 1000));
        this.setTime(Number(time));
    }
};


angular.module('app').service('TimeService', function () {
    this.hello = function () {
        return "hello";
    };


    //将时间戳转换成易读的格式
    this.unixTimeStamp2simpleDate = function (timestamp) {

        var unixTimestamp = new Date(timestamp);

        return unixTimestamp.format("yyyy-MM-dd");

    };

    //将时间戳转换成易读的格式
    this.unixTimeStamp2simpleDateTime = function (timestamp) {

        var unixTimestamp = new Date(timestamp);

        return unixTimestamp.format("yyyy-MM-dd HH:mm");

    };


    //将时间戳转换成易读的格式
    this.str2simpleDate = function (str) {
        if (!str) {
            return "未知时间";
        }


        var d = new Date();
        d.setISO8601(str);

        return d.format("yyyy-MM-dd");

    };

    this.time2simpleDateTime = function (str) {

        if (!str) {
            return "未知时间";
        }

        var d = new Date();
        str = ISODateString(str);
        d.setISO8601(str);
        return d.format("yyyy-MM-dd HH:mm");

    };

    //将时间戳转换成易读的格式
    this.str2simpleDateTime = function (str) {

        if (!str) {
            return "未知时间";
        }

        var d = new Date();
        d.setISO8601(str);
        return d.format("yyyy-MM-dd HH:mm");

    };

    //将时间戳转换成易读的格式
    this.str2simpleTime = function (str) {

        if (!str) {
            return "未知时间";
        }
        var d = new Date();
        d.setISO8601(str);
        return d.format("HH:mm");
    };

    this.format = function (date, formatString) {
        return date.format(formatString);
    };


});