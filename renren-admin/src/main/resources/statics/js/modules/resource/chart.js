function ziyuan() {
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
            data: ['整车工程中心','人力资源部']
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
                        return "{a|"+params.value+"个}"+"\n\n{b|增长2%}";
                    },
                    position: 'center',
                    textStyle: {
                        fontSize: '14',
                        fontWeight: 'normal',
                        color: '#fff'
                    }
                },
                data:[
                    {value:2628,name:'整车工程中心'},
                    {value:1797,name:'人力资源部'}
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
function ziyuan1() {
    var myChart = echarts.init(document.getElementById('statistics1'));

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
            data: ['整车工程中心','人力资源部']
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
                        return "{a|"+params.value+"个}"+"\n\n{b|增长2%}";
                    },
                    position: 'center',
                    textStyle: {
                        fontSize: '14',
                        fontWeight: 'normal',
                        color: '#fff'
                    }
                },
                data:[
                    {value:2628,name:'整车工程中心'},
                    {value:1797,name:'人力资源部'}
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