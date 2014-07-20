import java.lang.String


def zipName = 'cpv_2008_xml.zip'
def tempDirectoryPath = System.getProperty("java.io.tmpdir")
def fileUrl = 'http://simap.europa.eu/news/new-cpv/cpv_2008_xml.zip'

def tempDir = new File(tempDirectoryPath, 'CPV')
def zipFile = new File(tempDir, zipName)

if(!zipFile.exists())
{
    downloadZipFile()
}

def downloadZipFile()
{
    def httpClient = HttpClientBuilder.create().build()
    HttpGet request = new HttpGet(fileUrl)
    HttpResponse response = getHttpClient().execute(request)
}