namespace java com.thrift.auto

struct FileData{
    // 文件名
    1:required string fileName,
    // 文件数据
    2:required binary buff,

}

service FileService{

  // 传输文件
  bool uploadFile(1:FileData fileData);
}