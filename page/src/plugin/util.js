export const date = {
    distance: function (timestamp) {
        if (timestamp === undefined || timestamp == null)
            return "无数据";
        // 补全为13位
        let arrTimestamp = (timestamp + '').split('');
        for (let start = 0; start < 13; start++) {
            if (!arrTimestamp[start]) {
                arrTimestamp[start] = '0';
            }
        }
        timestamp = arrTimestamp.join('') * 1;

        let minute = 1000 * 60;
        let hour = minute * 60;
        let day = hour * 24;
        let now = new Date().getTime();
        let diffValue = now - timestamp;

        // 如果本地时间反而小于变量时间
        if (diffValue < 0) {
            return '不久前';
        }

        // 计算差异时间的量级
        let dayC = diffValue / day;
        let hourC = diffValue / hour;
        let minC = diffValue / minute;

        // 数值补0方法
        let zero = function (value) {
            if (value < 10) {
                return '0' + value;
            }
            return value;
        };
        if (dayC > 14) {
            return (function () {
                let date = new Date(timestamp);
                return date.getFullYear() + '年' + zero(date.getMonth() + 1) + '月' + zero(date.getDate()) + '日';
            })();
        } else if (dayC >= 1) {
            return parseInt(dayC) + "天前";
        } else if (hourC >= 1) {
            return parseInt(hourC) + "小时前";
        } else if (minC >= 1) {
            return parseInt(minC) + "分钟前";
        }
        return '刚刚';
    },
    format: (timestamp, code) => {
        let dateObj = new Date(parseInt(timestamp));
        const opt = {
            "y+": dateObj.getFullYear().toString(),        // 年
            "m+": (dateObj.getMonth() + 1).toString(),     // 月
            "d+": dateObj.getDate().toString(),            // 日
            "H+": dateObj.getHours().toString(),           // 时
            "M+": dateObj.getMinutes().toString(),         // 分
            "S+": dateObj.getSeconds().toString()          // 秒
        }
        for (let k in opt) {
            let ret = new RegExp("(" + k + ")").exec(code);
            if (ret) code = code.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
        }
        return code;
    }
}