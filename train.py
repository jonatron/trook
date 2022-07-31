from http.server import BaseHTTPRequestHandler, HTTPServer
import base64
import json
import urllib.request


hostName = "localhost"
serverPort = 8080


class MyServer(BaseHTTPRequestHandler):
    def do_GET(self):
        self.send_response(200)
        self.send_header("Content-type", "text/plain")
        self.end_headers()
        request = urllib.request.Request('https://api.rtt.io/api/v1/json/search/LBG')
        base64string = base64.b64encode(
            bytes(
                '%s:%s' % (
                    'rttapi_username',
                    'password'
                ),
                'ascii'
            )
        )
        request.add_header("Authorization", "Basic %s" % base64string.decode('utf-8'))
        result = urllib.request.urlopen(request)
        result_text = result.read()
        # print("result_text", result_text)
        obj = json.loads(result_text)
        for service in obj['services']:
            self.wfile.write(service['locationDetail']['destination'][0]['description'].encode('ascii', 'ignore'))
            self.wfile.write(b" ")
            self.wfile.write(service['locationDetail']['realtimeDeparture'].encode('ascii', 'ignore'))
            self.wfile.write(b"\r\n<br>\r\n")


if __name__ == "__main__":
    webServer = HTTPServer((hostName, serverPort), MyServer)
    print("Server started http://%s:%s" % (hostName, serverPort))

    try:
        webServer.serve_forever()
    except KeyboardInterrupt:
        pass

    webServer.server_close()
    print("Server stopped.")
