import fetch from '../../utils/fetch';

export function openNetDisk(dir) {
  return fetch({
    method: 'get',
    url: '/openNetDisk',
    params: {
      dir: dir
    }
  })
}

export function downloadFileFromUrl(url, dir) {
  return fetch({
    method: 'post',
    url: '/downloadFileFromUrl',
    data: {
      url: url,
      dir: dir
    }
  })
}

export function formatDate(date) {
  date = new Date(date);
  var y = date.getFullYear();
  var m = date.getMonth() + 1;
  var d = date.getDate();
  var h = date.getHours();
  var m1 = date.getMinutes();
  var s = date.getSeconds();
  m = m < 10 ? ("0" + m) : m;
  d = d < 10 ? ("0" + d) : d;
  h = h < 10 ? ("0" + h) : h;
  m1 = m1 < 10 ? ("0" + m1) : m1;
  s = s < 10 ? ("0" + s) : s;
  return y + "-" + m + "-" + d + " " + h + ":" + m1 + ":" + s;
}

