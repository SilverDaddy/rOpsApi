<script>
import { Line, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins
export default {
    extends: Line,
    props: ['chartData'],
    mixins: [reactiveProp],
    data: () => ({
        colorSets: [
            // 여러 그래프 사용을 위해서 색상표 예약 
            { fore: '#ff5252', back: '#ff5252' },
            { fore: '#0779e4', back: '#0779e4' },
            { fore: '#1aa6b7', back: '#1aa6b7' },
            { fore: '#ffb396', back: '#ffb396' },  
            { fore: '#d68060', back: '#d68060' },
            { fore: '#a2d5f2', back: '#a2d5f2' }, 
            { fore: '#86aba1', back: '#86aba1' },  
            { fore: '#ea97ad', back: '#ea97ad' }, 
            { fore: '#583d72', back: '#583d72' },
            { fore: '#ffe3d8', back: '#ffe3d8' },
            { fore: '#c75643', back: '#c75643' },
            { fore: '#222831', back: '#222831' },
            { fore: '#16697a', back: '#16697a' },
            { fore: '#fd3a69', back: '#fd3a69' },
            { fore: '#8f384d', back: '#8f384d' },
            { fore: '#9ddfd3', back: '#9ddfd3' },
            { fore: '#835858', back: '#835858' },
            { fore: '#0278ae', back: '#0278ae' },
            { fore: '#87431d', back: '#87431d' },
            { fore: '#ffc93c', back: '#ffc93c' },
            { fore: '#8d93ab', back: '#8d93ab' },
            { fore: '#424242', back: '#424242' },
            { fore: '#FF3D00', back: '#FF3D00' },
            { fore: '#4E342E', back: '#4E342E' },
            { fore: '#37474F', back: '#37474F' },
            { fore: '#FFC400', back: '#FFC400' },
            { fore: '#827717', back: '#827717' },
            { fore: '#2E7D32', back: '#2E7D32' },
            { fore: '#40C4FF', back: '#40C4FF' },
            { fore: '#CDDC39', back: '#CDDC39' },
            { fore: '#004D40', back: '#004D40' },
            { fore: '#7C4DFF', back: '#7C4DFF' },
            { fore: '#AD1457', back: '#AD1457' },
            { fore: '#BBDEFB', back: '#BBDEFB' },
            { fore: '#4A148C', back: '#4A148C' },
            { fore: '#9FA8DA', back: '#9FA8DA' },
            { fore: '#00BFA5', back: '#00BFA5' },
            { fore: '#D4E157', back: '#D4E157' },
            { fore: '#7CB342', back: '#7CB342' },
            { fore: '#B9F6CA', back: '#B9F6CA' },
            { fore: '#D50000', back: '#D50000' },
            { fore: '#D1C4E9', back: '#D1C4E9' },
            { fore: '#006064', back: '#006064' },
        ],
        options: { 
            scales: { 
                yAxes: [{ 
                    ticks: { beginAtZero: true, precision: 0 }, 
                    gridLines: { display: true },
                    scaleLabel: {
                        display: true,
                        labelString: "",
                        fontColor: "#616264",
                        fontSize: 13
                    }
                }], 
                xAxes: [{ gridLines: { display: false } }] ,
                
            },
            legend: { 
                display: true,
                labels: {
                    usePointStyle: true
                }
            },
            responsive: true,
            maintainAspectRatio: false 
        }
    }),
    mounted() { this.init() },
    methods: {
        init() {
            //alert("bbb:"+JSON.stringify(this.chartData));
            let data = this.chartData.chartData
            let datasets = []
            let pos = 0
            data.forEach((site) => {
                let colors = this.colorSets[pos]
                datasets.push({ 
                    label: site.label, 
                    borderWidth: 2, 
                    borderColor: colors.back, 
                    backgroundColor: colors.back, 
                    pointBorderColor: colors.fore, 
                    pointBackgroundColor: colors.fore,
                    pointStyle: 'rectRot',
                    pointRadius: 5,
                    fill: false, 
                    data: site.data 
                }) 
                pos++
            })
            this.datacollection = { labels: this.chartData.labels, datasets: datasets }
            this.options.scales.yAxes[0].scaleLabel.labelString = this.chartData.labelString
            this.render()
        },
        render() { 
            this.renderChart(this.datacollection, this.options)
        }
    },
	watch: {
        chartData () {
            this.init()
        }
	}
}
</script>

<style scoped>

</style>

