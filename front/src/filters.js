import Vue from 'vue'
import moment from "moment";

Vue.filter('dateViewFilter', value => {
    return moment(value,"DD-MM-yyyy").format('DD MMM yyyy')
})
