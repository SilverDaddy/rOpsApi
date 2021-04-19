<script>
//Importing Bar class from the vue-chartjs wrapper
import { Doughnut, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins
//Exporting this so it can be used in other components
export default {
    name: "Doughnut",
    extends: Doughnut,
    props: ['chartData'],
    mixins: [reactiveProp],
    data: () => ({
        colorSets: [
            // 여러 그래프 사용을 위해서 색상표 예약  
            { back: '#ff5252' },
            { back: '#0779e4' },
            { back: '#1aa6b7' },
            { back: '#ffb396' },  
            { back: '#d68060' },
            { back: '#a2d5f2' }, 
            { back: '#86aba1' },  
            { back: '#ea97ad' }, 
            { back: '#583d72' },
            { back: '#ffe3d8' },
            { back: '#c75643' },
            { back: '#222831' },
            { back: '#16697a' },
            { back: '#fd3a69' },
            { back: '#8f384d' },
            { back: '#9ddfd3' },
            { back: '#835858' },
            { back: '#0278ae' },
            { back: '#87431d' },
            { back: '#ffc93c' },
            { back: '#8d93ab' },
            { back: '#424242' },
            { back: '#FF3D00' },
            { back: '#4E342E' },
            { back: '#37474F' },
            { back: '#FFC400' },
            { back: '#827717' },
            { back: '#2E7D32' },
            { back: '#40C4FF' },
            { back: '#CDDC39' },
            { back: '#004D40' },
            { back: '#7C4DFF' },
            { back: '#AD1457' },
            { back: '#BBDEFB' },
            { back: '#4A148C' },
            { back: '#9FA8DA' },
            { back: '#00BFA5' },
            { back: '#D4E157' },
            { back: '#7CB342' },
            { back: '#B9F6CA' },
            { back: '#D50000' },
            { back: '#D1C4E9' },
            { back: '#006064' },
        ],
        options: { 
            responsive: true, 
            legend: { 
                display: true,
            }, 
            maintainAspectRatio : false, 
            cutoutPercentage:55, 
            animation:false,
            circumference: Math.PI,
            rotation: -Math.PI,
        }
    }),
    mounted() { this.init() },
    methods: {
        init() {
            var chartdata = this.chartData.chartData
            var label = "%";
            var backgroundColor = [];
            let datasets = []
            let pos = 0
            chartdata.forEach((site) => {
                backgroundColor[pos] = this.colorSets[pos].back
                chartdata[pos] = site
                pos++
            })
            datasets.push({ 
                label: label,
                backgroundColor: backgroundColor, 
                data: chartdata
            }) 

            this.datacollection = { labels: this.chartData.labels, datasets: datasets }
            this.render()
        },
        render() { 
            this.renderChart(this.datacollection, this.options);
        }
    },
    watch: {
		chartData() {
            this.init();
		}
    }
}
</script>