import axios from 'axios';
import {Message} from 'element-ui';

const service = axios.create({
    baseURL:'http://localhost:8111',
    timeout:60000
});

service.interceptors.request.use((config) => {
    config.headers['dajiaId'] = 'aaa';
    return config;
},(err) => {
    return Promise.reject(err);
}),

service.interceptors.response.use(
    response => {
        return response.data;
},error => {
        Message({
            message:'网络请求异常',
            type:error,
            duration:3 * 1000
        });
        return Promise.reject(error);
}
)
export default service
