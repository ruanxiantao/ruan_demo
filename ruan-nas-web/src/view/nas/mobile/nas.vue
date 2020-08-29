<template>
  <div>
    <el-table :data="diskData" ref="singleTable" width="100%">
      <el-table-column property="fileName" label="文件名" min-width="70%">
        <template slot-scope="scope">
          <span class="el-icon-folder" v-if="scope.row.fileType === 'directory'"></span>
          <img src='../../../assets/logo.png' width="10px" height="10px" alt="" v-else>
          <el-button type="text" @click="openNetDisk(scope.row.filePath)" v-if="scope.row.fileType === 'directory'">
            <span v-if="scope.row.fileName == null || scope.row.fileName === ''">
              {{scope.row.filePath}}
            </span>
            <span v-else>
              {{scope.row.fileName}}
            </span>
          </el-button>
          <el-button type="text" @click="openFile(scope.row.filePath)" v-else>
            {{scope.row.fileName}}
          </el-button>
        </template>
      </el-table-column>
      <!--<el-table-column property="fileSize" label="大小"></el-table-column>-->
      <!--<el-table-column property="fileExt" label="类型"></el-table-column>-->
      <!--<el-table-column property="lastModifiedTime" label="修改时间"></el-table-column>-->
      <el-table-column label="下载" min-width="30%">
        <template slot-scope="scope">
          <el-button type="text" @click="downloadFileInfo(scope.row)" v-if="scope.row.fileType === 'file'">
            <span class="el-icon-download"></span>
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <br/>
    <el-dialog title="操作" :visible.sync="dialogOuterFormVisible" width="100%">
      <el-dialog title="资源链接" :visible.sync="dialoginnerFormVisible" width="80%" append-to-body>
        <el-form :model="form">
          <el-form-item label="资源url" label-width="20%">
            <el-input v-model="form.url" auto-complete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialoginnerFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="downloadFileFromUrl">确 定</el-button>
        </div>
      </el-dialog>
      <div slot="footer" class="dialog-footer">
        <el-form :model="formInfo" class="demo-form-inline" style="text-align: left">
          <el-form-item label="上传文件到目标路径" label-width="50%">
            <el-input v-model="dir" :disabled="false" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="通过url转存资源到本云盘" label-width="50%">
            <el-button @click="dialoginnerFormVisible = true" style="width: 80%">创建任务</el-button>
          </el-form-item>
        </el-form>
        <el-upload
          class="upload-demo"
          drag
          :action="uploadUrl"
          :data="uploadFileInfo"
          :show-file-list="true"
          :file-list="formInfo.fileList"
          :on-success="uploadSuccess"
          :on-preview="downloadFileInfo"
          multiple>
          <i class="el-icon-upload"></i>
          <div class="el-upload__text"><em>上传文件到当前目录</em></div>
        </el-upload>
        <el-button @click="dialogOuterFormVisible = false">取消</el-button>
      </div>
    </el-dialog>
    <div>
      <el-button @click="dialogOuterFormVisible = true" class="buttomLocate1" v-if="dir !== '/'">操作</el-button>
    </div>
    <div>
      <el-button @click="goback" class="buttomLocate2" v-if="dir !== '/'">返回</el-button>
    </div>
  </div>
</template>
<script>
  import {openNetDisk, formatDate, downloadFileFromUrl} from '../../../js/nas/nas';

  export default {
    components: {},
    data() {
      return {
        dialogOuterFormVisible: false,
        dialoginnerFormVisible: false,
        uploadUrl: process.env.BASE_API + '/multipartFile',
        dir: '/',
        uploadFileInfo: {
          dir: ''
        },
        form: {
          url: ''
        },
        diskData: [{fileName: 22, fileSize: 11, lastModifiedTime: 1990}, {
          fileName: 22,
          fileSize: 11,
          lastModifiedTime: 1990
        }, {fileName: 22, fileSize: 11, lastModifiedTime: 1990}],//网盘资源
        formInfo: {
          type: '',
          content: '',
          fileList: [],
        },
      };
    },
    created() {
      this.openNetDisk("/");
    },
    methods: {
      downloadFileFromUrl() {
        if (this.form.url == null || this.form.url === '') {
          alert("url不能为空");
          return;
        }
        if (this.dir == null || this.dir === '') {
          alert("目标路径不能为空");
          return;
        }
        downloadFileFromUrl(encodeURI(this.form.url), this.dir);
        this.dialogFormVisible = false;
      },
      goback() {
        let number = this.dir.lastIndexOf("/");
        if (number > 0) {
          // 如果斜线在最后一位，代表这是根路径 windows系统需要特殊处理
          if (this.dir.length === number + 1) {
            this.dir = '/';
            this.openNetDisk(this.dir);
          } else {
            this.dir = this.dir.substring(0, number + 1);
            this.openNetDisk(this.dir);
          }
        }
      },
      openFile(path) {
        this.$router.push({
          path: '/filedetailmobile',
          query: {url: path}
        })
      },
      openNetDisk(dir) {
        this.dir = dir;
        this.uploadFileInfo.dir = dir;
        openNetDisk(this.dir).then(res => {
          if (res.success) {
            let data = res.data;
            for (let fileInfo of data) {
              if (fileInfo.fileType == 'file') {
                if (fileInfo.fileSize > 1024 * 1024 * 1024 * 1024) {
                  let size = (fileInfo.fileSize / 1024 / 1024 / 1024).toString();
                  fileInfo.fileSize = size.substring(0, size.indexOf('.') + 2) + 'G';
                } else if (fileInfo.fileSize > 1024 * 1024 * 1024) {
                  let size = (fileInfo.fileSize / 1024 / 2014).toString();
                  fileInfo.fileSize = size.substring(0, size.indexOf('.') + 1) + 'M';
                } else {
                  let size = (fileInfo.fileSize / 1024).toString();
                  fileInfo.fileSize = size.substring(0, size.indexOf('.')) + 'K';
                }
              } else {
                fileInfo.fileSize = '--'
              }
              fileInfo.filePath = fileInfo.filePath.replace(/\\/g, "/");
              fileInfo.lastModifiedTime = formatDate(fileInfo.lastModifiedTime);
            }
            this.diskData = data;
            // this.$refs["singleTable"].$nextTick(function () {
            //   this.escrollY(
            //     ".el-table__body-wrapper",
            //     ".el-table__header-wrapper"
            //   );
            // })
          }
        });
      },
      uploadSuccess(response, file) {
        this.formInfo.fileList.push({
          name: file.name,
          url: response.data,
          fileName: file.name,
          filePath: response.data
        })
      },
      downloadFileInfo(file) {
        window.location.href = process.env.BASE_API + "/downloadFileInfo?filePath=" + encodeURI(file.filePath);
      },
    }
  }
</script>
<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }

  .buttomLocate1 {
    position: fixed;
    bottom: 100px;
    right: 0;
    text-align: right;
  }

  .buttomLocate2 {
    position: fixed;
    bottom: 0;
    right: 0;
    text-align: right;
  }

  .upload {
    width: 80%;
  }
</style>
