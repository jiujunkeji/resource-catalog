function ziyuan() {
    var myChart = echarts.init(document.getElementById('statistics'));

    option = {
        color:['#10ac84','#ffa514','#00dcff','#2593f3','#0b56a7'],
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
            data: ['整车工程中心','人力资源部','用户体验及功能开发中心	','路特斯工程（中国）','吉利品牌研究院NL项目组']
        },
        series : [
            {
                name: '部门使用情况',
                type: 'pie',
                radius : '55%',
                center: ['50%', '55%'],
                label:{
                    show:true,
                    formatter:'{b}: {d}%'
                },
                data:[
                    {value:2628,name:'整车工程中心'},
                    {value:1797,name:'人力资源部'},
                    {value:1399,name:'用户体验及功能开发中心'},
                    {value:1241,name:'路特斯工程（中国）'},
                    {value:1211,name:'吉利品牌研究院NL项目组'}
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