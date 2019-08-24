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
        color:['#ffa514','#3949ab','#00dcff','#2593f3','#0b56a7'],
        title : {
            text: '',
            subtext: '',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            // formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'horizontal',
            top:'20',
            // icon:'circle',
            itemWidth:30,
            itemHeight:11,
            itemGap:16,
            data: ['往日访问量','当日访问量']
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
                        return "{a|"+data.callAll+"}"+"\n\n{b|总访问量}";
                    },
                    position: 'center',
                    textStyle: {
                        fontSize: '14',
                        fontWeight: 'normal',
                        color: '#fff'
                    }
                },
                data:[
                    {value:data.callToday,name:'当日访问量'},
                    {value:num,name:'往日访问量'}
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