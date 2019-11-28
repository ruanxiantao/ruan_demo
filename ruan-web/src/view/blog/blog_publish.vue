<template>
  <div>
    <div>
      <el-form :inline="true" :model="formInfo" class="demo-form-inline"  style="text-align: left">
        <el-form-item label="博客技术分类">
          <el-input v-model="formInfo.type" placeholder="博客技术分类"></el-input>
        </el-form-item>
        <el-button @click="openNetDisk(dir)">
          打开网盘
        </el-button>
        <el-form-item label="上传文件到目标路径">
          <el-input v-model="dir" disabled="false"></el-input>
        </el-form-item>
      </el-form>
    </div>

    <el-dialog
      title="我的网盘"
      :visible.sync="dialogVisible"
      size="tiny">
      <el-table :data="diskData">
        <el-table-column property="fileName" label="文件名" width="200" cell>
          <template  slot-scope="scope">

            <img src = '../../assets/logo.png'width="10px" height="10px">
            <el-button type="text" @click="openNetDisk(scope.row.filePath)" v-if="scope.row.fileType == 'directory'">
              {{scope.row.fileName}}
            </el-button>
            <span v-else>
              {{scope.row.fileName}}
            </span>
          </template>
        </el-table-column>
        <el-table-column property="fileSize" label="大小" width="150"></el-table-column>
        <el-table-column property="lastModifiedTime" label="修改时间"></el-table-column>
      </el-table>
    </el-dialog>
    <div class="edit_container">
      <quill-editor
        v-model="formInfo.content"
        ref="myQuillEditor"
        :options="editorOption"
        @blur="onEditorBlur($event)" @focus="onEditorFocus($event)"
        @change="onEditorChange($event)">
      </quill-editor>
      <div style="text-align: left">
      </div>
      <br/>
      <br/>
    </div>
    <div style="text-align: left">
      <el-upload
        class="upload-demo"
        drag
        :action="uploadUrl"
        :show-file-list="true"
        :file-list="formInfo.fileList"
        :on-success="uploadSuccess"
        :on-preview="downloadFileInfo"
        multiple>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      </el-upload>
    </div>
    <el-button @click="saveBlog" type="primary">保存</el-button>
  </div>
</template>
<script>
  import { quillEditor } from 'vue-quill-editor'
  import * as Quill from 'quill' //引入编辑器
  import { ImageDrop } from 'quill-image-drop-module';
  Quill.register('modules/imageDrop', ImageDrop);
  import {insertBlog,openNetDisk,formatDate} from '../../js/blog/blog';
  import ElButton from "../../../node_modules/element-ui/packages/button/src/button.vue";
  import ElDialog from "../../../node_modules/element-ui/packages/dialog/src/component.vue";
  import ElFormItem from "../../../node_modules/element-ui/packages/form/src/form-item.vue";
  import ElInput from "../../../node_modules/element-ui/packages/input/src/input.vue";
  export default {
    components: {
      ElInput,
      ElFormItem,
      ElDialog,
      ElButton},
    data() {
      return {
        baseUrl:'http://localhost:8111',
        uploadUrl: 'http://localhost:8111/multipartFile',
        dir:'/',
        diskData:[],//网盘资源
        dialogVisible:false,//网盘对话框
        formInfo: {
          type: '',
          content: '',
          fileList: [],
        },

        editorOption:{
          modules:{
            imageDrop:true,
          },
          theme:'snow'
        },
        modules:{
          toolbar:[
            ['bold', 'italic', 'underline', 'strike'],    //加粗，斜体，下划线，删除线
            ['blockquote', 'code-block'],     //引用，代码块


            [{ 'header': 1 }, { 'header': 2 }],        // 标题，键值对的形式；1、2表示字体大小
            [{ 'list': 'ordered'}, { 'list': 'bullet' }],     //列表
            [{ 'script': 'sub'}, { 'script': 'super' }],   // 上下标
            [{ 'indent': '-1'}, { 'indent': '+1' }],     // 缩进
            [{ 'direction': 'rtl' }],             // 文本方向


            [{ 'size': ['small', false, 'large', 'huge'] }], // 字体大小
            [{ 'header': [1, 2, 3, 4, 5, 6, false] }],     //几级标题


            [{ 'color': [] }, { 'background': [] }],     // 字体颜色，字体背景颜色
            [{ 'font': [] }],     //字体
            [{ 'align': [] }],    //对齐方式


            ['clean'],    //清除字体样式
            ['image','video']    //上传图片、上传视频

          ]
        },
        theme:'snow'
      };
    },
    computed: {
      editor() {
        return this.$refs.myQuillEditor.quill;
      },
    },
    methods: {
      openNetDisk(dir){
        this.dir = dir;
        openNetDisk(this.dir).then(res => {
          if(res.success){
            let data = res.data;
            for (let fileInfo of data) {
              if(fileInfo.fileType == 'file'){
                if(fileInfo.fileSize > 1024 * 1024 * 1024 * 1024){
                  let size = (fileInfo.fileSize/1024/1024/1024).toString();
                  fileInfo.fileSize = size.substring(0,size.indexOf('.') + 2) + 'G';
                }else if(fileInfo.fileSize > 1024 * 1024 * 1024){
                  let size = (fileInfo.fileSize/1024/2014).toString();
                  fileInfo.fileSize = size.substring(0,size.indexOf('.') + 1) + 'M';
                }else {
                  let size = (fileInfo.fileSize/1024).toString();
                  fileInfo.fileSize = size.substring(0,size.indexOf('.')) + 'K';
                }
              }else {
                fileInfo.fileSize = '--'
              }
              fileInfo.lastModifiedTime = formatDate(fileInfo.lastModifiedTime);
            }
            this.diskData = data;
            if(dir != '/'){
              this.uploadUrl = this.uploadUrl + '?dir=' + dir;
            }
            this.dialogVisible = true;
          }
        });
      },
      uploadSuccess(response,file){
        this.formInfo.fileList.push({
          name:file.name,
          url:response.data,
          fileName:file.ame,
          filePath:response.data
        })
      },
      downloadFileInfo(file){
        window.location.href = this.baseUrl + "/downloadFileInfo?filePath=" + file.filePath;
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      onEditorReady(editor) { // 准备编辑器
      },
      onEditorBlur(){}, // 失去焦点事件
      onEditorFocus(){}, // 获得焦点事件
      onEditorChange(){}, // 内容改变事件
      saveBlog:function(){
        insertBlog(this.formInfo).then(res => {
          if(res.success){
            this.$message({
              message:'添加博客成功',
              type:'success'
            });
          }
        })
      }
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
</style>
