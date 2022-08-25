import Vue from 'vue'
import moment from "moment";

Vue.filter('dateViewFilter', value => {
    // return moment.utc(value).local().format('DD MMM YY h:mm A')
    return moment(value,"DD-MM-yyyy").format('DD MMM yyyy')
})