function ziyuan(data) {
    var myChart = echarts.init(document.getElementById('statistics'));

    var dataStyle = {
        normal: {
            label: {
                show: false
            },
            labelLine: {
                show: false
            },
            shadowBlur: 0,
            shadowColor: '#203665'
        }
    };
    console.log(data);
    var num = data.callAll - data.callToday;

    option = {
        backgroundColor: '#fff',
        grid: {
            top: '15%',
            right: '10%',
            left: '10%',
            bottom: '12%'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        xAxis: [{
            type: 'category',
            color: '#777',
            data: ['基础元数据', '网络安全元数据', '位置元数据', '社交网络元数据', '日志元数据','特征库元数据','画像库元数据'],
            axisLabel: {
                margin: 20,
                color: '#777',
                textStyle: {
                    fontSize: 14
                },
            },
            axisLine: {
                lineStyle: {
                    color: '#d2d2d2',
                }
            },
            axisTick: {
                show: false
            },
        }],
        yAxis: [{
            min: 0,
            max: 100,
            axisLabel: {
                formatter: '{value}',
                color: '#999',
                textStyle: {
                    fontSize: 14
                },
            },
            axisLine: {
                lineStyle: {
                    color: 'rgba(107,107,107,0.37)',
                }
            },
            axisTick: {
                show: false
            },
            splitLine: {
                lineStyle: {
                    color: 'rgba(131,101,101,0.2)',
                    type: 'dashed',
                }
            }
        }],
        series: [{
            type: 'bar',
            data: [40, 90, 10, 20, 56, 20, 90],
            barWidth: '50px',
            itemStyle: {
                normal: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                        offset: 0,
                        color: '#409eff' // 0% 处的颜色
                    }, {
                        offset: 1,
                        color: '#409eff' // 100% 处的颜色
                    }], false),
                    barBorderRadius: [30, 30, 0, 0],
                }
            },
            label: {
                show: true,
                fontSize: 16,
                // fontWeight: 'bold',
                position: 'top',
                color: '#999',
            }
        }]
    };
    // option = {
    //     color:['#ffa514','#3949ab','#00dcff','#2593f3','#0b56a7'],
    //     title : {
    //         text: '',
    //         subtext: '',
    //         x:'center'
    //     },
    //     tooltip : {
    //         trigger: 'item',
    //         // formatter: "{a} <br/>{b} : {c} ({d}%)"
    //     },
    //     legend: {
    //         orient: 'horizontal',
    //         top:'20',
    //         // icon:'circle',
    //         itemWidth:30,
    //         itemHeight:11,
    //         itemGap:16,
    //         data: ['往日访问量','当日访问量']
    //     },
    //     series : [
    //         {
    //             name: '',
    //             type: 'pie',
    //             radius: ['40%', '55%'],
    //             center: ['50%', '55%'],
    //             label:{
    //                 show:true,
    //                 rich: {
    //                     a: {
    //                         color: '#454545',
    //                         align: 'center',
    //                         fontSize: 20,
    //                         fontWeight: "bold"
    //                     },
    //                     b: {
    //                         color: '#454545',
    //                         align: 'center',
    //                         fontSize: 16
    //                     }
    //                 },
    //                 formatter: function(params){
    //                     return "{a|"+data.callAll+"}"+"\n\n{b|总访问量}";
    //                 },
    //                 position: 'center',
    //                 textStyle: {
    //                     fontSize: '14',
    //                     fontWeight: 'normal',
    //                     color: '#fff'
    //                 }
    //             },
    //             data:[
    //                 {value:data.callToday,name:'当日访问量'},
    //                 {value:num,name:'往日访问量'}
    //             ],
    //             itemStyle: {
    //                 emphasis: {
    //                     shadowBlur: 10,
    //                     shadowOffsetX: 0,
    //                     shadowColor: 'rgba(0, 0, 0, 0.5)'
    //                 }
    //             }
    //         }
    //     ]
    // };

    myChart.setOption(option);

}
function ziyuan1(data) {
    var myChart = echarts.init(document.getElementById('statistics1'));
    var num = data.catalogAll - data.catalogNew;
    var dataStyle = {
        normal: {
            label: {
                show: false
            },
            labelLine: {
                show: false
            },
            shadowBlur: 0,
            shadowColor: '#203665'
        }
    };

    option = {
        color:['#ffa514','#3949ab','#00dcff','#2593f3','#0b56a7'],
        title : {
            text: '',
            subtext: '',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'horizontal',
            top:'20',
            // icon:'circle',
            itemWidth:30,
            itemHeight:11,
            itemGap:16,
            data: ['往日目录数','当日目录数']
        },
        series : [
            {
                name: '',
                type: 'pie',
                radius: ['40%', '55%'],
                center: ['50%', '55%'],
                label:{
                    show:true,
                    rich: {
                        a: {
                            color: '#454545',
                            align: 'center',
                            fontSize: 20,
                            fontWeight: "bold"
                        },
                        b: {
                            color: '#454545',
                            align: 'center',
                            fontSize: 16
                        }
                    },
                    formatter: function(params){
                        return "{a|"+data.catalogAll+"}"+"\n\n{b|总目录数}";
                    },
                    position: 'center',
                    textStyle: {
                        fontSize: '14',
                        fontWeight: 'normal',
                        color: '#fff'
                    }
                },
                data:[
                    {value:data.catalogNew,name:'当日目录数'},
                    {value:num,name:'往日目录数'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    myChart.setOption(option);

}