import React, {Component} from 'react';
import FormLayout from '../layout/FormLayout';
import MainLayout from "../layout/MainLayout";

class UploadImage extends Component {
    render() {
        return (
            <div>
                <MainLayout showNavigation={true}>
                    <FormLayout title="Upload image" subtitle="Food images ">
                        <form onSubmit={this.handleSubmit}>
                            <button type="button" className="btn btn-primary">Create</button>
                            {this.state.redirect ? <Redirect to={"/"}/> : ""}
                        </form>
                    </FormLayout>
                </MainLayout>
            </div>
        );
    };

    async convert(){
        var http = require("https");

        var options = {
            "method": "POST",
            "hostname": "api.imgur.com",
            "path": ["3", this.base64("image.jpeg", function(dataUrl){console.log('RESULT:', dataUrl)})],
            "headers": {
                "content-type": "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW",
                "Authorization": "Bearer 045935a2ab842325b9ad70831b71eda9258b8528,Bearer eb04ef1c18258dd644cec2346e4d59244d241f80",
                "cache-control": "no-cache",
                "Postman-Token": "aee2816c-8f95-4e32-a4c5-22a150f4b54a"
            }
        };

        var req = http.request(options, function (res) {
            var chunks = [];

            res.on("data", function (chunk) {
                chunks.push(chunk);
            });

            res.on("end", function () {
                var body = Buffer.concat(chunks);
                console.log(body.toString());
            });
        });

        req.write("------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"image\"\r\n\r\nR0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
        req.end();
    }

    async base64(file, callback){
        const upFile = {};
        function readerOnload(e){
            const base64 = btoa(e.target.result);
            upFile.base64 = base64;
            callback(upFile)
        };

        const reader = new FileReader();
        reader.onload = readerOnload;

        var file = file[0].files[0];
        upFile.filetype = file.type;
        upFile.size = file.size;
        upFile.filename = file.name;
        reader.readAsBinaryString(file);
    }
}
