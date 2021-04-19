<script>
import { Line, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins
//Exporting this so it can be used in other components
export default {
    name: 'singleLine-chart', 
    extends: Line,
    props: ['chartData'],
    mixins: [reactiveProp],
    data () {
      return {
        datacollection: {
          //Data to be represented on x-axis
          labels: [], 
          datasets: [
            {
              label: 'Saving Time(Hour)',
              backgroundColor: '#A5D6A7',
              pointBackgroundColor: '#43A047',
              borderWidth: 1,
              pointBorderColor: '#43A047',
              //Data to be represented on y-axis
              data: []
            }
          ]
        },
        //Chart.js options that controls the appearance of the chart
        options: {
          scales: {
            yAxes: [{
              ticks: {
                beginAtZero: true
              },
              gridLines: {
                display: true
              }
            }],
            xAxes: [ {
              gridLines: {
                display: false
              }
            }]
          },
          legend: {
            display: true
          },
          responsive: true,
          maintainAspectRatio: false
        }
      }
    },
    mounted() {
        this.init()
    },
    methods: {
            init() {
                this.datacollection.labels = this.chartData.labels
                this.datacollection.datasets[0].data = this.chartData.chartData
                this.render()
            },
            render() { 
                this.renderChart(this.datacollection, this.options)
            }
        },
    watch: {
        chartData() {
            this.init()
        }
    }
  }
</script>
