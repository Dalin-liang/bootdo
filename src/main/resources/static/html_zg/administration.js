/**
 * Created by Administrator on 2017/5/17.
 */
var months=['january','february','march','april','may','june','july','august','september','october','november','december'];
var monthsC=['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'];
var nowMonth=new Date().getMonth();
var startMonth=nowMonth-6<0?nowMonth-6+12:nowMonth-6;
var endMonth=nowMonth-1<0?nowMonth-1+12:nowMonth-1;
$(function(){
	AnQuan();
});

function AnQuan(){
    // 总业务量分析
    $(function(){
        var tmp=new Array(12);
        var myChart = echarts.init($("#shijian01")[0]);
        /*$.ajax({
            url:'/api/nt/governBusiness/getTotalBusiness',
            type:'get',
            dataType:'json',
            data:{
                ssq:'2019'
            },
            success:function(data){

                for(var i=startMonth;i<endMonth+1;i++){
                    var list=data.data.totalBusinessList[0];
                    tmp[i]=parseInt(list[months[i]]);
                }
                option = {
                    tooltip: {
                        trigger: "item",
                        formatter: "{a} <br/>{b} : {c}"
                    },
                    legend: {
                        x: 'center',
                        data: ["总业务量分析"],
                        textStyle:{
                            color:"#e9ebee"

                        },
                        show:false
                    },
                    xAxis: [
                        {
                            type: "category",
                            name: "日期",
                            axisTick: {
                                alignWithLabel: true
                            },
                            splitLine:{show: false},
                            axisLabel : {
                                formatter: '{value}',
                                textStyle: {
                                    color: 'white',
                                    align: 'right'
                                }
                            },
                            nameTextStyle:{
                                color:'white'
                            },
                            axisPointer: {
                                label: {
                                    formatter: function (params) {
                                        return '业务量  ' + params.value
                                            + (params.seriesData.length ? '：' + params.seriesData[0].data : '');
                                    }
                                }
                            },
                            data : monthsC.slice(startMonth+1,endMonth+1)
                        }
                    ],
                    grid:{
                        borderWidth:0
                    },
                    yAxis: [
                        {
                            type: "value",
                            name: "数量",
                            splitLine:{show: false},
                            axisLabel : {
                                formatter: '{value} 件',
                                textStyle: {
                                    color: 'white',
                                    align: 'right'
                                }
                            },
                            nameTextStyle:{
                                color:'white'
                            }
                        }
                    ],

                    calculable: false,
                    series: [
                        {
                            name: "总业务量分析",
                            type: "line",
                            data:tmp.slice(startMonth+1,endMonth+1),
                            itemStyle: {
                                normal: {
                                    color:"#0aabff"
                                }
                            }

                        }

                    ]
                };
                myChart.setOption(option);
            }
        })*/
        option = {
                    tooltip: {
                        trigger: "item",
                        formatter: "{a} <br/>{b} : {c}"
                    },
                    legend: {
                        x: 'center',
                        data: ["总业务量分析"],
                        textStyle:{
                            color:"#e9ebee"

                        },
                        show:false
                    },
                    xAxis: [
                        {
                            type: "category",
                            name: "日期",
                            axisTick: {
                                alignWithLabel: true
                            },
                            splitLine:{show: false},
                            axisLabel : {
                                formatter: '{value}',
                                textStyle: {
                                    color: 'white',
                                    align: 'right'
                                }
                            },
                            nameTextStyle:{
                                color:'white'
                            },
                            axisPointer: {
                                label: {
                                    formatter: function (params) {
                                        return '业务量  ' + params.value
                                            + (params.seriesData.length ? '：' + params.seriesData[0].data : '');
                                    }
                                }
                            },
                            data : ['1月','2月','3月','4月']
                        }
                    ],
                    grid:{
                        borderWidth:0
                    },
                    yAxis: [
                        {
                            type: "value",
                            name: "数量",
                            splitLine:{show: false},
                            axisLabel : {
                                formatter: '{value} 件',
                                textStyle: {
                                    color: 'white',
                                    align: 'right'
                                }
                            },
                            nameTextStyle:{
                                color:'white'
                            }
                        }
                    ],

                    calculable: false,
                    series: [
                        {
                            name: "总业务量分析",
                            type: "line",
                            data:['1750','1108','2230','2280'],
                            itemStyle: {
                                normal: {
                                    color:"#0aabff"
                                }
                            }

                        }

                    ]
                };
        myChart.setOption(option)
    });
// 事件级别分析
    $(function(){
        var myChart = echarts.init($("#shijian02")[0]);
        var tmp=[];
        $.ajax({
            url:'/api/nt/governBusiness/getTypeBusiness',
            type:'get',
            dataType:'json',
            data:{
                ssq:'2019'
            },
            success:function(data){
                for(var i in data.data.typeBusinessList){
                    var t=[];
                    t.push(data.data.typeBusinessList[i].name);
                    for(var j=startMonth+1;j<endMonth+1;j++){
                        t.push(data.data.typeBusinessList[i][months[j]]==null?0:data.data.typeBusinessList[i][months[j]])
                    }
                    tmp.push(t);
                }


        console.log(tmp);
        var placeHoledStyle = {
            normal:{
                barBorderColor:'rgba(0,0,0,0)',
                color:'rgba(0,0,0,0)'
            },
            emphasis:{
                barBorderColor:'rgba(0,0,0,0)',
                color:'rgba(0,0,0,0)'
            }
        };
        var dataStyle = {
            normal: {
                label : {
                    show: true,
                    position: 'insideLeft',
                    formatter: '{c}%'
                }
            }
        };
        option = {

            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                },
                formatter : '{b}<br/>{a0}:{c0}<br/>{a1}:{c1}<br/>{a2}:{c2}<br/>{a3}:{c3}<br/>{a4}:{c4}<br/>{a5}:{c5}'
            },
            /*legend: {
                y: 55,
                data:[tmp[0][0], tmp[1][0],tmp[2][0], tmp[3][0],tmp[4][0],tmp[5][0]],
                textStyle:{
                    color:"#fff"

                }
            }, */
            legend: {
                y: 55,
                data:['城乡保险','城乡社保','来穗管理','计生','民政','农办'],
                textStyle:{
                    color:"#fff"

                }
            },

            grid: {
                y: 80,
                y2: 30
            },
            xAxis : [
                {
                    type : 'value',
                    position: 'top',
                    splitLine: {show: false},
                    axisLabel: {show: false}
                }
            ],
            /*yAxis : [
                {
                    type : 'category',
                    splitLine: {show: false},
                    axisLabel : {

                        textStyle: {
                            color: '#fff',
                            align: 'right'
                        }
                    },
                    data : monthsC.slice(startMonth+1,endMonth+1)
                }
            ],*/
            yAxis : [
                {
                    type : 'category',
                    splitLine: {show: false},
                    axisLabel : {

                        textStyle: {
                            color: '#fff',
                            align: 'right'
                        }
                    },
                    data : ['1月','2月','3月','4月']
                }
            ],
            /*series : [
                {
                    name:tmp[0][0],
                    type:'bar',
                    stack: '总量',
                    data:['1月','2月','3月','4月'],
                    itemStyle: {
                        normal: {
                            color:"#1afffd"
                        }
                    }
                },
                {
                    name:tmp[1][0],
                    type:'bar',
                    stack: '总量',
                    itemStyle: placeHoledStyle,
                    data:[tmp[1][1],tmp[1][2],tmp[1][3],tmp[1][4],tmp[1][5]],
                    itemStyle: {
                        normal: {
                            color:"#2e7cff"
                        }
                    }
                },
                {
                    name:tmp[2][0],
                    type:'bar',
                    stack: '总量',
                    itemStyle : dataStyle,
                    data:[tmp[2][1],tmp[2][2],tmp[2][3],tmp[2][4],tmp[2][5]],
                    itemStyle: {
                        normal: {
                            color:"#ffcb89"
                        }
                    }
                },
                {
                    name:tmp[3][0],
                    type:'bar',
                    stack: '总量',
                    itemStyle: placeHoledStyle,
                    data:[tmp[3][1],tmp[3][2],tmp[3][3],tmp[3][4],tmp[3][5]],
                    itemStyle: {
                        normal: {
                            color:"#005ea1"
                        }
                    }
                },
                {
                    name:tmp[4][0],
                    type:'bar',
                    stack: '总量',
                    itemStyle : dataStyle,
                    data:[tmp[4][1],tmp[4][2],tmp[4][3],tmp[4][4],tmp[4][5]],
                    itemStyle: {
                        normal: {
                            color:"#0ad5ff"
                        }
                    }
                },
                {
                    name:tmp[5][0],
                    type:'bar',
                    stack: '总量',
                    itemStyle: placeHoledStyle,
                    data:[tmp[5][1],tmp[5][2],tmp[5][3],tmp[5][4],tmp[5][5]],
                    itemStyle: {
                        normal: {
                            color:"#e15828"
                        }
                    }
                }
            ]*/
            series : [
                {
                    name:'城乡保险',
                    type:'bar',
                    stack: '总量',
                    data:[240,100,300,320],
                    itemStyle: {
                        normal: {
                            color:"#1afffd"
                        }
                    }
                },
                {
                    name:'城乡社保',
                    type:'bar',
                    stack: '总量',
                    itemStyle: placeHoledStyle,
                    data:[430,300,450,500],
                    itemStyle: {
                        normal: {
                            color:"#2e7cff"
                        }
                    }
                },
                {
                    name:'来穗管理',
                    type:'bar',
                    stack: '总量',
                    itemStyle : dataStyle,
                    data:[200,80,380,460],
                    itemStyle: {
                        normal: {
                            color:"#ffcb89"
                        }
                    }
                },
                {
                    name:'计生',
                    type:'bar',
                    stack: '总量',
                    itemStyle: placeHoledStyle,
                    data:[450,388,500,490],
                    itemStyle: {
                        normal: {
                            color:"#005ea1"
                        }
                    }
                },
                {
                    name:'民政',
                    type:'bar',
                    stack: '总量',
                    itemStyle : dataStyle,
                    data:[430,240,600,510],
                    itemStyle: {
                        normal: {
                            color:"#0ad5ff"
                        }
                    }
                },
                {
                    name:'农办',
                    type:'bar',
                    stack: '总量',
                    itemStyle: placeHoledStyle,
                    data:[0,0,0,21],
                    itemStyle: {
                        normal: {
                            color:"#e15828"
                        }
                    }
                }
            ]
        };
        myChart.setOption(option);
            }
        })
    });
// 满意度分析1
    $(function(){
        var myChart = echarts.init($("#shijian03")[0]);
        $.ajax({
            url:'/api/nt/governServe/getSatisfied',
            dataType:'json',
            type:'get',
            data:{
                ssq:'2019'
            },
            success:function(data){
                console.log(data.data);
                $(".w1").text(data.data.satisfiedList.appealTypeTotal+"个");
                $(".w2").text(data.data.satisfiedList.handleTotal);
                $(".w3").text(data.data.satisfiedList.maxAppeal);
                $(".w4").text(data.data.satisfiedList.minAppeal);
            }
        })
        var labelMy = {
                normal : {
                    label : {
                        show : true,
                        position : 'center',
                        formatter : '池田村',
                        textStyle: {
                            baseline : 'bottom',
                            color:"#fff"
                            
                        }
                    },
                    labelLine : {
                        show : false
                    }
                }
            };
        var labelBmy = {
                normal : {
                    label : {
                        show : true,
                        position : 'center',
                        formatter : '到尉村',
                        textStyle: {
                            baseline : 'bottom',
                            color:"#fff"
                        }
                    },
                    labelLine : {
                        show : false
                    }
                }
            };
        var labelWpj = {
                normal : {
                    label : {
                        show : true,
                        position : 'center',
                        formatter : '麦村',
                        textStyle: {
                            baseline : 'bottom',
                            color:"#fff"
                        }
                    },
                    labelLine : {
                        show : false
                    }
                }
            };
        var labelTop = {
            normal : {
                label : {
                    show : true,
                    position : 'center',
                    formatter : '{b}',
                    textStyle: {
                        baseline : 'bottom'
                    }
                },
                labelLine : {
                    show : false
                }
            }
        };
        var labelFromatter = {
            normal : {
                label : {
                    formatter : function (params){
                        return 100 - params.value + '%'
                    },
                    textStyle: {
                        baseline : 'top'
                    }
                    
                }
        		
            },
        }
        var labelBottom = {
            normal : {
                color: '#111b21',
                label : {
                    show : true,
                    position : 'center'
                },
                labelLine : {
                    show : false
                }
            },
            emphasis: {
                color: 'rgba(0,0,0,0)',
                show: true,
                textStyle: {
                    fontSize: '30',
                    fontWeight: 'bold'
                }
            }
        };
        var radius = [40, 55];
        option = {
            color:['#64BD3D','#FFAA33','#0000FF'],
            legend: {
                x : 'center',
                textStyle:{
                    color:"#fff"

                },
                selectedMode:true,
                color: ['#0ad5ff','#ffcb89', '#2e7cff','#80f63e', '#feb602', '#fefc3e'],
                data:['满意','不满意','未评价']
            },
            grid:{
              top:0
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            series : [
                {
                    type : 'pie',
                    center : ['20%', '58%'],
                    radius : radius,
                    x: '20%', // for funnel
                    name:"池田村",
                    data : [
                        {value:250, name:'满意',itemStyle:labelMy},
		                {value:45, name:'不满意',itemStyle:labelMy},
		                {value:78, name:'未评价',itemStyle:labelMy}
                    ]
                    
                    
                },
                {
                    type : 'pie',
                    center : ['50%', '58%'],
                    radius : radius,
                    x: '20%', // for funnel
                    name:"到尉村",
                    data : [
                        {value:330, name:'满意',itemStyle:labelBmy},
		                {value:113, name:'不满意',itemStyle:labelBmy},
		                {value:70, name:'未评价',itemStyle:labelBmy}
                    ]
                    
                },
                {
                    type : 'pie',
                    center : ['80%', '58%'],
                    radius : radius,
                    x: '20%', // for funnel
                    name:"麦村",
                    data : [
                        {value:289, name:'满意',itemStyle:labelWpj},
		                {value:96, name:'不满意',itemStyle:labelWpj},
		                {value:68, name:'未评价',itemStyle:labelWpj}
                    ]
                    
                }
            ]
        };
        myChart.setOption(option);
    });
 /*// 满意度分析2
    $(function(){
        var myChart = echarts.init($("#shijian03_2")[0]);
        var labelMy = {
                normal : {
                    label : {
                        show : true,
                        position : 'center',
                        formatter : '白面石村',
                        textStyle: {
                            baseline : 'bottom',
                            color:"#fff"
                            
                        }
                    },
                    labelLine : {
                        show : false
                    }
                }
            };
        var labelBmy = {
                normal : {
                    label : {
                        show : true,
                        position : 'center',
                        formatter : '畲族村',
                        textStyle: {
                            baseline : 'bottom',
                            color:"#fff"
                        }
                    },
                    labelLine : {
                        show : false
                    }
                }
            };
        var labelWpj = {
                normal : {
                    label : {
                        show : true,
                        position : 'center',
                        formatter : '蒙花布村',
                        textStyle: {
                            baseline : 'bottom',
                            color:"#fff"
                        }
                    },
                    labelLine : {
                        show : false
                    }
                }
            };
        var labelTop = {
            normal : {
                label : {
                    show : true,
                    position : 'center',
                    formatter : '{b}',
                    textStyle: {
                        baseline : 'bottom'
                    }
                },
                labelLine : {
                    show : false
                }
            }
        };
        var labelFromatter = {
            normal : {
                label : {
                    formatter : function (params){
                        return 100 - params.value + '%'
                    },
                    textStyle: {
                        baseline : 'top'
                    }
                    
                }
        		
            },
        }
        var labelBottom = {
            normal : {
                color: '#111b21',
                label : {
                    show : true,
                    position : 'center'
                },
                labelLine : {
                    show : false
                }
            },
            emphasis: {
                color: 'rgba(0,0,0,0)',
                show: true,
                textStyle: {
                    fontSize: '30',
                    fontWeight: 'bold'
                }
            }
        };
        var radius = [40, 55];
        option = {
            legend: {
                x : 'center',
                textStyle:{
                    color:"#fff"

                },
                selectedMode:true,
                color: ['#0ad5ff','#ffcb89', '#2e7cff','#80f63e', '#feb602', '#fefc3e'],
                data:['满意','不满意','未评价']
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            series : [
                {
                    type : 'pie',
                    center : ['20%', '58%'],
                    radius : radius,
                    x: '20%', // for funnel
                    name:"白石面村",
                    data : [
                        {value:250, name:'满意',itemStyle:labelMy},
		                {value:45, name:'不满意',itemStyle:labelMy},
		                {value:78, name:'未评价',itemStyle:labelMy}
                    ]
                    
                    
                },
                {
                    type : 'pie',
                    center : ['50%', '58%'],
                    radius : radius,
                    x: '20%', // for funnel
                    name:"畲族村",
                    data : [
                        {value:330, name:'满意',itemStyle:labelBmy},
		                {value:113, name:'不满意',itemStyle:labelBmy},
		                {value:70, name:'未评价',itemStyle:labelBmy}
                    ]
                    
                },
                {
                    type : 'pie',
                    center : ['80%', '58%'],
                    radius : radius,
                    x: '20%', // for funnel
                    name:"蒙花布村",
                    data : [
                        {value:289, name:'满意',itemStyle:labelWpj},
		                {value:96, name:'不满意',itemStyle:labelWpj},
		                {value:68, name:'未评价',itemStyle:labelWpj}
                    ]
                    
                }
            ]
        };
        myChart.setOption(option);
    });*/
// 事发时间特征分析
    $(function(){
       var myChart = echarts.init($("#shijian04")[0]);
        option = {
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data:['逾期退单工单', '重办工单','紧急工单','重复工单','其它'],
                textStyle:{
                    color:"#fff"

                }
            },
            xAxis : [
                {
                    type : 'value',
                    splitLine: {show: false},
                    axisLabel : {
                        textStyle: {
                            color: '#fff',
                            align: 'right'
                        }
                    },
                }
            ],
            yAxis : [
                {
                    type : 'category',
                    splitLine: {show: false},
                    axisLabel : {

                        textStyle: {
                            color: '#fff',
                            align: 'right'
                        }
                    },
                    data: ['白面石', '畲族村', '蒙花布村', '池田村', '黄屋村', '汀塘村', '合水店村', '大冚村', '水口村', '银杨村', '麦村村','乌头石村','圭湖村','番丰村','何屋村','浪拔村','亮星村','石溪村','水围村','庙尾村','东汾村','花园村','岳村村']
                }
            ],
            series : [
                {
                    name:'逾期退单工单',
                    type:'bar',
                    stack: '总量',
                    itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
                    data: [12, 13, 10, 16, 19, 23, 21, 23, 20, 26, 27, 30,12, 12, 11, 14, 19, 23, 21, 20, 20, 25, 28, 30]
                    ,
                    itemStyle: {
                        normal: {
                            color:"#0ad5ff"
                        }
                    }
                },
                {
                    name:'重办工单',
                    type:'bar',
                    stack: '总量',
                    itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
                    data: [1, 13, 0, 16, 19, 23, 21, 23, 20, 26, 27, 30,12, 12, 11, 14, 19, 23, 21, 20, 0, 25, 8, 30]
                    ,
                    itemStyle: {
                        normal: {
                            color:"#005ea1"
                        }
                    }
                },
                {
                    name:'紧急工单',
                    type:'bar',
                    stack: '总量',
                    itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
                    data: [12, 13, 10, 16, 19, 3, 2, 3, 0, 6, 9, 30,12, 12, 11, 14, 1, 3, 1, 2, 0, 5, 2, 0]
                    ,
                    itemStyle: {
                        normal: {
                            color:"#ffcb89"
                        }
                    }
                },
                {
                    name:'重复工单',
                    type:'bar',
                    stack: '总量',
                    itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
                    data: [2, 13, 10, 6, 19, 23, 21, 3, 20, 6, 7, 30,12, 12, 11, 14, 19, 3, 21, 0, 20, 5, 8, 0]
                    ,
                    itemStyle: {
                        normal: {
                            color:"#2e7cff"
                        }
                    }
                },
                {
                    name:'其它',
                    type:'bar',
                    stack: '总量',
                    itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
                    data: [1, 3, 10, 6, 9, 3, 11, 3, 0, 6, 7, 0,2, 2, 1, 4, 9, 3, 1, 0, 0, 5, 8, 3]
                    ,
                    itemStyle: {
                        normal: {
                            color:"#1afffd"
                        }
                    }
                }
            ]
        };

        myChart.setOption(option);
    });
}


