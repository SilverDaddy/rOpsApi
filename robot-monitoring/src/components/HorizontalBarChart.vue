<script>

import { HorizontalBar, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins
//Exporting this so it can be used in other components
export default {
  name: 'horizontalBar-chart',
  extends: HorizontalBar,
  props: ['chartData'],
  mixins: [reactiveProp],
  data() {
    return {
      datacollection: {
        //Data to be represented on x-axis
        labels: [],
        datasets: [
          {
            label: 'Saving Time(Hour)',
            backgroundColor: '#F48FB1',
            hoverBackgroundColor: '#F06292',
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