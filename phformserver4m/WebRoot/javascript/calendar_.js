/**
 * 日历组件
 *
 */
Calendar = Class.create();
Calendar.construct = function() {
	
    var that = this;
    
    //日期类型 0:年月日;1:年月日时分
    var type = 0;
    //触发日历组件的源dom元素
    var inp = null;
    //日历组件的最外层容器元素
    var obj = null;
	//贴在obj下面, 否则遇到select这类元素, 显示层次有问题
    var iframe = null;
    
    //obj的子节点
    var fieldset = null;
    
    //fieldset的内部元素
    var select1 = null;
    var select2 = null;
    var table = null;
	var time = null;
    var foot = null;

    /**
     * 当数字<10时, 前面添0
     * @param num 数字
     *
     */
	this.checkNum = function(num) {
		return (num<10) ? ('0'+num) : (num + '');
	};
		
	//时间
    var dt = null;
    //年
    var year = null;
    //月
    var month = null;
    //日期
    var day = null;
    //时
    var hour = null;
    //分
    var minute = null;
    
    //月份数组
	var arr_month = new Array('\u4e00\u6708', '\u4e8c\u6708', '\u4e09\u6708', '\u56db\u6708', '\u4e94\u6708', '\u516d\u6708', '\u4e03\u6708', '\u516b\u6708', '\u4e5d\u6708', '\u5341\u6708', '\u5341\u4e00\u6708', '\u5341\u4e8c\u6708');
	//日期数组
	var arr_day = new Array('\u65e5', '\u4e00', '\u4e8c', '\u4e09', '\u56db', '\u4e94', '\u516d');
		
	/**
	 * 初始化方法
	 *
	 */
	this.initialize = function() {
	};
		
	/**
	 * 设置日期组件的样式
     *		
	 */
	this.objParam = function() {
		with(obj.style) {
			backgroundColor = '#FFFFFE';
			display = 'inline';
			position = 'absolute';
			zIndex = 9999;
		}
		obj.id = 'calendar';
		with(fieldset.style) {
			position = 'relative';
			width = 180;
			top = 2;
			//backgroundColor = '#EEFFDD';
			border = '1 #8080c0 solid';
		}
		with(select1.style) {
			position = 'relative';
			fontSize = 12;
			left = 10;
			top = 2;
			width = 70;
		}
		with(select2.style) {
			position = 'relative';
			fontSize = 12;
			left = 28;
			top = 2;
			width = 70;
		}
		//下拉框添加内容
		that.addOption();
		//table添加样式
		that.tableParam();
	};
		
	/**
	 * 设置table的样式
	 *
	 */
	this.tableParam = function() {
		with(table.style) {
			position = 'relative';
			left = 6;
			top = 4;
			width = 164;
		}
		//table.cellpadding = 30;
		//table.cellspacing = 10;
		//table.rules = 'none';
	};
		
	/**
	 * 设置日历内部事件
	 *
	 */
	this.objEvent = function() {
		select1.onchange = that.month_change.closure(select1);
		select2.onchange = that.year_change.closure(select2);
		obj.onkeypress = that.keypress.closure(obj);
	};		
		
	/**
	 * 入口方法
	 * @param e 触发日历组件的源dom元素
	 * @param tp 类型
	 *
	 */
	this.show = function(e, tp) {
		inp = e;
		type = tp;
		var value = inp.value;
		if(value) {
			year = value.substr(0,4);
			month = value.substr(5,2);
			day = value.substr(8,2);
			if(type) {
				hour = value.substr(11, 2) && !isNaN(value.substr(11, 2)) ? value.substr(11, 2) : '00';
				minute = value.substr(14, 2) && !isNaN(value.substr(14, 2)) ? value.substr(14, 2) : '00';
			}
		}
		else {
	    	dt = new Date();
	    	year = dt.getYear();
	    	month = that.checkNum(dt.getMonth()+1);
	    	day = that.checkNum(dt.getDate());
	    	hour = that.checkNum(dt.getHours());
	    	minute = that.checkNum(dt.getMinutes());
		}
		that.drop();
		that.elementCreate();
		that.objParam();
		that.objEvent();
		var arr = that.getOffset(e);
		if(arr[0] - arr[2] + obj.offsetWidth < document.body.scrollWidth)
			obj.style.left = arr[0] + 2;
		else
			obj.style.left = arr[0] - Math.abs(obj.offsetWidth - e.offsetWidth);
		if(arr[1] - arr[3] + obj.offsetHeight + e.offsetHeight < document.body.scrollHeight)
			obj.style.top = arr[1] + e.offsetHeight + 2 - arr[3];
		else
			obj.style.top = arr[1] - 2 - obj.offsetHeight - arr[3];
        iframe = document.createElement("iframe");
        iframe.style.position = "absolute";
        iframe.style.border = "0px outset #f0f0f0";
        iframe.style.zIndex = "999";
        iframe.style.pixelLeft = obj.offsetLeft;
        iframe.style.pixelTop =obj.offsetTop;
        iframe.style.pixelHeight = obj.offsetHeight;
        iframe.style.pixelWidth = obj.offsetWidth;
        document.body.insertAdjacentElement("beforeEnd",iframe);
        select1.focus();
        //foot.lastChild.focus();
	};
    
    /**
     * 得到元素的绝对位置
     * @param e 元素
     */
    this.getOffset = function(e) {
        var arr = [0, 0, 0, 0];
        do {
            arr[0] += e.offsetLeft;
            arr[1] += e.offsetTop;
            arr[2] += e.scrollLeft;
            arr[3] += e.scrollTop;
        }while((e = e.offsetParent))
        return arr;
    };
		
	/**
	 * 删除日期组件
	 *
	 */
	this.drop = function() {
		var old = document.getElementsByName('calendar');
		for(var i=0;i<old.length;i++) {
			old[i].removeNode(true);
			old[i] = null;
		}
        if(iframe!=null) {
            iframe.removeNode(true);
        }
    };
		
	/**
	 * 创建日历组件的内部元素
	 *
	 */
	this.elementCreate = function() {
		obj = document.createElement('div');
		fieldset = document.createElement('fieldset');
		var legend = document.createElement('legend');
		legend.innerText = '\u65e5\u671f(D)';
		legend.style.fontSize = 12;
		select1 = document.createElement('select');
		select2 = document.createElement('select');
		table = document.createElement('table');
		that.tableContent(year, month);
		fieldset.appendChild(legend);
		fieldset.appendChild(select1);
		fieldset.appendChild(select2);
		if(type) {
			that.addTime();
		}
		fieldset.appendChild(table);
		foot = document.createElement('div');
		that.temporary(foot);
       	fieldset.appendChild(foot);
		obj.appendChild(fieldset);
		document.body.appendChild(obj);
	};
	
	/**
	 * 添加确定,删除,清空 
	 * @param foot
	 *
	 */
	this.temporary = function(foot) {
		var clear = document.createElement('a');
		clear.innerText = '\u6e05\u9664';
		clear.style.position = 'relative';
		clear.style.left = 80;
		clear.style.fontSize = '12';
		clear.onmouseover = function() {
			this.style.cursor='hand';
			this.style.backgroundColor = '#8080c0';	
		};
		clear.onmouseout = function() {
			this.style.backgroundColor = '#FFFFFE';
		};
		clear.onclick = function() {
			inp.value = '';
		};
		var close = document.createElement('a');
		close.innerText = '\u5173\u95ed';
		close.style.position = 'relative';
		close.style.left = 90;
		close.style.fontSize = '12';
		close.onmouseover = function() {
			this.style.cursor='hand';
			this.style.backgroundColor = '#8080c0';	
		};
		close.onmouseout = function() {
			this.style.backgroundColor = '#FFFFFE';
		};
		close.onclick = function() {
			that.drop();
			inp.focus();
		};
		var sure = document.createElement('a');
		sure.innerText = '\u786e\u5b9a';
		sure.style.position = 'relative';
		sure.style.left = 100;
		sure.style.fontSize = '12';
		sure.onmouseover = function() {
			this.style.cursor='hand';
			this.style.backgroundColor = '#8080c0';	
		};
		sure.onmouseout = function() {
			this.style.backgroundColor = '#FFFFFE';
		};
		sure.onclick = function() {
			that.setValue();
			that.drop();
			inp.focus();
		};
		foot.appendChild(clear);
		foot.appendChild(close);
		foot.appendChild(sure);
    };
		
	/**
	 * 添加下拉框的option
	 *
	 */
	this.addOption = function() {
		for(var i=0;i<arr_month.length;i++) {
			select1.options[i] = document.createElement('option');
			select1.options[i].value = i+1;
			select1.options[i].text = arr_month[i];
			if((i+1) == month) {
				select1.options[i].selected = true;
			}
		}
		for(var i=0;i<150;i++) {
			select2.options[i] = document.createElement('option');
			select2.options[i].value = i+1900;
			select2.options[i].text = i+1900;
			if((i+1900) == year) {
				select2.options[i].selected = true;
			}
		}
	};
		
	this.keypress = function() {
		if(27 == event.keyCode)
			that.drop();
	};
		
	/**
	 * 添加table的内部元素
	 * @param year 年
	 * @param month 月
	 *
	 */
	this.tableContent = function(year,month) {
		var len = arr_day.length;
		var curDate = new Date(year+'/'+month+'/1');
		var cur_day = curDate.getDay();
		var day_id = 1;
		//添加头
		var row = table.insertRow(table.rows.length);
		for(var i=0;i<len;i++) {
			var cell = row.insertCell(i);
			cell.innerText = arr_day[i];
			cell.align = 'center';
			cell.style.fontSize = 12;
		}
		//添加第一行日期
		row = table.insertRow(table.rows.length);
		for(var i=0;i<len;i++) {
			if(i<cur_day) {
				var cell = row.insertCell(i);
			}else {
				var cell = row.insertCell(i);
				cell.innerText = day_id++;
				that.cellEvent(cell);
				that.cellParam(cell, i);
			}
		}
		var max = that.maxDay(year,month);
		var rows = parseInt((max-day_id+1)/7);
		for(var i=0;i<rows;i++) {
			row = table.insertRow(table.rows.length);
			for(var j=0;j<len;j++) {
				var cell = row.insertCell(j);
				cell.innerText = day_id++;
				that.cellEvent(cell);
				that.cellParam(cell, j);
			}
		}
		row = table.insertRow(table.rows.length);
		for(var i=0;i<len;i++) {
			if(day_id <= max) {
				var cell = row.insertCell(i);
				cell.innerText = day_id++;
				that.cellEvent(cell);
				that.cellParam(cell, i);
			}else {
				var cell = row.insertCell(i);
			}
		}
				
		if(table.rows.length < 7) {
			row = table.insertRow(table.rows.length);
			for(var i=0;i<7;i++) {
				var cell = row.insertCell(i);
				cell.innerHTML = '&nbsp;';
				cell.style.fontSize = '12';
			}
		}
	};
		
	/**
	 * 计算一个月中的天数
	 * @param year 年
	 * @param month 月
	 */
	this.maxDay = function(year,month) {
		switch(month) {
			case '01':
			case '03':
			case '05':
			case '07':
			case '08':
			case '10':
			case '12':
				return 31;
			case '04':
			case '06':
			case '09':
			case '11':
				return 30;
			case '02':
				return (new Date(year+"/2/29").getMonth() == 1) ? 29 : 28;
			default:
				return 30;
		}
	};
		 
	/**
	 * 改变月的事件
	 *
	 */
	this.month_change = function() {
		month = that.checkNum(this.value);
		table.removeNode(true);
		foot.removeNode(true);
		table = document.createElement('table');
		that.tableParam();
		fieldset.appendChild(table);
		//foot = that.temporary();
		fieldset.appendChild(foot);
		that.tableContent(year, month);
		//that.setValue();
	};
		
	/**
	 * 改变年的事件
	 *
	 */
	this.year_change = function() {
		year = this.value;
		table.removeNode(true);
		foot.removeNode(true);
		table = document.createElement('table');
		that.tableParam();
		fieldset.appendChild(table);
		//foot = that.temporary();
		fieldset.appendChild(foot);
		that.tableContent(year, month);
		//that.setValue();
	};
		
	/**
	 * 鼠标移动到日期上的事件
	 *
	 */
	this.mouseover = function() {
		this.style.backgroundColor = '#8080c0';
	};
		
	/**
	 * 鼠标移出日期的事件
	 *
	 */
	this.mouseout = function() {
		this.style.backgroundColor = '';
	};
		
	/**
	 * 日期样式
	 * @param cell 日期元素
	 * @param i 星期几
	 */
	this.cellParam = function(cell, i) {
		cell.style.cursor = 'hand';
		if('TD' == cell.nodeName) {
			cell.style.fontSize = 12;
			cell.align = 'center';
		}else {
			cell.style.position = 'relative';
			cell.style.fontSize = 13;
		}
		if(0 == i || 6 == i) {
			cell.style.color = 'red';
		}
	};
		
	/**
	 * 日期点击事件
	 *
	 */
	this.cellClick = function() {
		day = that.checkNum(this.innerText);
		that.setValue();
		that.drop()
		inp.focus();
	};
		
	/**
	 * 日期事件
	 * @param cell 日期元素
	 */		
	this.cellEvent = function(cell) {
		cell.onmouseover = that.mouseover.closure(cell);
		cell.onmouseout = that.mouseout.closure(cell);
		cell.onclick = that.cellClick.closure(cell);
		if(day == cell.innerText) {
			cell.onmouseover();
			cell.onmouseout = null;
		}
	};

	/**
	 * 设值
	 *
	 */
	this.setValue = function() {
		if(!type) {
			inp.value = year + '-' + month + '-' + day;
		}else {
			inp.value = year + '-' + month + '-' + day + ' ' + hour + ':' + minute;
		}
	};
		
	/**
	 * 添加时间
	 *
	 */
	this.addTime = function() {
		time = document.createElement('div');
		var h = document.createElement('span');
		var m = document.createElement('span');
		time.appendChild(h);
		time.appendChild(m);
		fieldset.appendChild(time);
		h.innerText = hour + '\u65f6';
		m.innerText = minute + '\u5206';
		time.style.position = 'relative';
		that.cellParam(h);
		that.cellParam(m);
		time.style.top = 4;
		h.style.left = 62;
		m.style.left = 66;
		that.timeEvent(h, m);
	};
		
	/**
	 * 时间事件
	 * @param h 时
	 * @param m 分
	 */
	this.timeEvent = function(h, m) {
		h.onclick = that.hour_click.closure(h);
		m.onclick = that.minute_click.closure(m);
	};
		
	/**
	 * 时点击事件
	 * 
	 */
	this.hour_click = function() {
		while(time.childNodes.length>2) {
			time.lastChild.onchange();
		}
		var slt_h = document.createElement('select');
		slt_h.style.position = 'relative';
		slt_h.style.top = 2;
		slt_h.style.left = -4;
		slt_h.style.width = 42;
		slt_h.style.fontSize = 12;
		for(var i=0;i<24;i++) {
			slt_h.options[i]= document.createElement('option');
			slt_h.options[i].value = i;
			slt_h.options[i].text = i+'\u65f6';
			if(hour == i) {
				slt_h.options[i].selected = true;
			}
		}
		time.appendChild(slt_h);
		slt_h.onchange = that.hour_change.closure(slt_h);
	};
	
	/**
	 * 时改变事件
	 *
	 */
	this.hour_change = function() {
		hour = that.checkNum(this.value);
		this.removeNode(true);
		time.firstChild.innerText = hour + '\u65f6';
	};
	
	/**
	 * 分改变事件
	 *
	 */
	this.minute_change = function() {
		minute = that.checkNum(this.value);
		this.removeNode(true);
		time.lastChild.innerText = minute + '\u5206';
	};
		
	/**
	 * 分点击事件
	 *
	 */
	this.minute_click = function() {
		while(time.childNodes.length>2) {
			time.lastChild.onchange();
		}
		var slt_m = document.createElement('select');
		slt_m.style.position = 'relative';
		slt_m.style.top = 2;
		slt_m.style.left = 34;
		slt_m.style.width = 42;
		slt_m.style.fontSize = 12;
		for(var i=0;i<60;i++) {
			slt_m.options[i]= document.createElement('option');
			slt_m.options[i].value = i;
			slt_m.options[i].text = i+'\u5206';
			if(minute == i) {
				slt_m.options[i].selected = true;
			}
		}
		time.appendChild(slt_m);
		slt_m.onchange = that.minute_change.closure(slt_m);
	};
}
//创建日历组件的一个实例
var clr = new Calendar();