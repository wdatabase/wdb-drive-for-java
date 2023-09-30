package io.github.wdatabase.wdbdrive;

import java.util.ArrayList;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Wdb {
    private String host;
    private String authKey;

    final private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Wdb(String host, String key) {
        this.host = host;
        this.authKey = key;
    }

    public ApiRsp CreateObj(String key, String data, ArrayList<String> categories) {
        ArrayList<String> ccategories = Optional.ofNullable(categories).orElse(new ArrayList<>());

        long tm = this.ctime();
        String sg = this.sign(String.format("%s%d%s%s", this.authKey, tm, key, data));

        PutDataReq req = new PutDataReq();
        req.setKey(key);
        req.setCategories(ccategories);
        req.setContent(data);
        req.setTime(tm);
        req.setSg(sg);

        ApiRsp rsp = new ApiRsp();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String raw = mapper.writeValueAsString(req);

            rsp = this.post("/wdb/api/create", raw);
        } catch (JsonProcessingException e) {
            logger.error(String.format("%s", e));
            rsp.setCode(400);
            rsp.setMsg("putObj json encode err");
        }

        return rsp;
    }

    public ApiRsp UpdateObj(String key, String data) {
        long tm = this.ctime();
        String sg = this.sign(String.format("%s%d%s%s", this.authKey, tm, key, data));

        UpdateDataReq req = new UpdateDataReq();
        req.setKey(key);
        req.setContent(data);
        req.setTime(tm);
        req.setSg(sg);

        ApiRsp rsp = new ApiRsp();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String raw = mapper.writeValueAsString(req);

            rsp = this.post("/wdb/api/update", raw);
        } catch (JsonProcessingException e) {
            logger.error(String.format("%s", e));
            rsp.setCode(400);
            rsp.setMsg("UpdateObj json encode err");
        }

        return rsp;
    }

    public ApiRsp GetObj(String key) {
        long tm = this.ctime();
        String sg = this.sign(String.format("%s%d%s", this.authKey, tm, key));
        return this.get(String.format("/wdb/api/get?key=%s&time=%d&sg=%s", key, tm, sg));
    }

    public ApiRsp DelObj(String key) {
        long tm = this.ctime();
        String sg = this.sign(String.format("%s%d%s", this.authKey, tm, key));
        return this.get(String.format("/wdb/api/del?key=%s&time=%d&sg=%s", key, tm, sg));
    }

    public ListRsp ListObj(String category, long offset, long limit, String order) {
        long tm = this.ctime();
        String sg = this.sign(String.format("%s%s%d", this.authKey, category, tm));
        ApiRsp apiRsp = this.get(String.format("/wdb/api/list?category=%s&offset=%d&limit=%d&order=%s&time=%d&sg=%s",
                category, offset, limit, order, tm, sg));
        ListRsp listRsp = new ListRsp();
        if (apiRsp.getCode() == 200) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                ListData list_data = mapper.readValue(apiRsp.getData(), ListData.class);

                listRsp.setCode(200);
                listRsp.setTotal(list_data.getTotal());
                listRsp.setList(list_data.getList());

            } catch (Exception e) {
                logger.error(String.format("%s", e));
                listRsp.setCode(400);
                listRsp.setMsg("ListData decode");
            }
        } else {
            listRsp.setCode(400);
            listRsp.setMsg(apiRsp.getMsg());
        }

        return listRsp;
    }

    public ApiRsp UploadByPath(String path, String key, ArrayList<String> categories) {
        ArrayList<String> ccategories = Optional.ofNullable(categories).orElse(new ArrayList<>());

        long tm = this.ctime();
        String sg = this.sign(String.format("%s%d%s", this.authKey, tm, key));

        UploadReq req = new UploadReq();
        req.setPath(path);
        req.setKey(key);
        req.setCategories(ccategories);
        req.setTime(tm);
        req.setSg(sg);

        ApiRsp rsp = new ApiRsp();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String raw = mapper.writeValueAsString(req);

            rsp = this.post("/wdb/api/upload", raw);
        } catch (JsonProcessingException e) {
            logger.error(String.format("%s", e));
            rsp.setCode(400);
            rsp.setMsg("putObj json encode err");
        }

        return rsp;
    }

    public ApiRsp DownToPath(String path, String key) {
        long tm = this.ctime();
        String sg = this.sign(String.format("%s%d%s", this.authKey, tm, key));

        DownReq req = new DownReq();
        req.setPath(path);
        req.setKey(key);
        req.setTime(tm);
        req.setSg(sg);

        ApiRsp rsp = new ApiRsp();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String raw = mapper.writeValueAsString(req);

            rsp = this.post("/wdb/api/down", raw);
        } catch (JsonProcessingException e) {
            logger.error(String.format("%s", e));
            rsp.setCode(400);
            rsp.setMsg("putObj json encode err");
        }

        return rsp;
    }

    public ApiRsp TransBegin(ArrayList<String> keys) {
        ArrayList<String> ckeys = Optional.ofNullable(keys).orElse(new ArrayList<String>());

        long tm = this.ctime();
        String sg = this.sign(String.format("%s%d", this.authKey, tm));

        TransBeginReq req = new TransBeginReq();
        req.setKeys(ckeys);
        req.setTime(tm);
        req.setSg(sg);

        ApiRsp rsp = new ApiRsp();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String raw = mapper.writeValueAsString(req);

            rsp = this.post("/wdb/api/trans/begin", raw);
        } catch (JsonProcessingException e) {
            logger.error(String.format("%s", e));
            rsp.setCode(400);
            rsp.setMsg("TransBegin json encode err");
        }

        return rsp;
    }

    public ApiRsp TransCreateObj(String tsid, String key, String data, ArrayList<String> categories) {
        ArrayList<String> ccategories = Optional.ofNullable(categories).orElse(new ArrayList<>());

        long tm = this.ctime();
        String sg = this.sign(String.format("%s%d%s%s%s", this.authKey, tm, key, data, tsid));

        TransPutDataReq req = new TransPutDataReq();
        req.setTsid(tsid);
        req.setKey(key);
        req.setCategories(ccategories);
        req.setContent(data);
        req.setTime(tm);
        req.setSg(sg);

        ApiRsp rsp = new ApiRsp();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String raw = mapper.writeValueAsString(req);

            rsp = this.post("/wdb/api/trans/create", raw);
        } catch (JsonProcessingException e) {
            logger.error(String.format("%s", e));
            rsp.setCode(400);
            rsp.setMsg("CreateRawData json encode err");
        }

        return rsp;
    }

    public ApiRsp TransUpdateObj(String tsid, String key, String data) {
        long tm = this.ctime();
        String sg = this.sign(String.format("%s%d%s%s%s", this.authKey, tm, key, data, tsid));

        TransUpdateDataReq req = new TransUpdateDataReq();
        req.setTsid(tsid);
        req.setKey(key);
        req.setContent(data);
        req.setTime(tm);
        req.setSg(sg);

        ApiRsp rsp = new ApiRsp();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String raw = mapper.writeValueAsString(req);

            rsp = this.post("/wdb/api/trans/update", raw);
        } catch (JsonProcessingException e) {
            logger.error(String.format("%s", e));
            rsp.setCode(400);
            rsp.setMsg("CreateRawData json encode err");
        }

        return rsp;
    }

    public ApiRsp TransGet(String tsid, String key) {
        long tm = this.ctime();
        String sg = this.sign(String.format("%s%d%s%s", this.authKey, tm, key, tsid));
        return this.get(String.format("/wdb/api/trans/get?tsid=%s&key=%s&time=%d&sg=%s", tsid, key, tm, sg));
    }

    public ApiRsp TransDelObj(String tsid, String key) {
        long tm = this.ctime();
        String sg = this.sign(String.format("%s%d%s%s", this.authKey, tm, key, tsid));
        return this.get(String.format("/wdb/api/trans/del?tsid=%s&key=%s&time=%d&sg=%s", tsid, key, tm, sg));
    }

    public ApiRsp transCommit(String tsid) {
        long tm = this.ctime();
        String sg = this.sign(String.format("%s%d%s", this.authKey, tm, tsid));

        TransIdReq req = new TransIdReq();
        req.setTsid(tsid);
        req.setTime(tm);
        req.setSg(sg);

        ApiRsp rsp = new ApiRsp();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String raw = mapper.writeValueAsString(req);

            rsp = this.post("/wdb/api/trans/commit", raw);
        } catch (JsonProcessingException e) {
            logger.error(String.format("%s", e));
            rsp.setCode(400);
            rsp.setMsg("transCommit json encode err");
        }

        return rsp;
    }

    public ApiRsp TransRollBack(String tsid) {
        long tm = this.ctime();
        String sg = this.sign(String.format("%s%d%s", this.authKey, tm, tsid));

        TransIdReq req = new TransIdReq();
        req.setTsid(tsid);
        req.setTime(tm);
        req.setSg(sg);

        ApiRsp rsp = new ApiRsp();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String raw = mapper.writeValueAsString(req);

            rsp = this.post("/wdb/api/trans/roll_back", raw);
        } catch (JsonProcessingException e) {
            logger.error(String.format("%s", e));
            rsp.setCode(400);
            rsp.setMsg("TransRollBack json encode err");
        }

        return rsp;
    }

    public ApiRsp CreateIndex(ArrayList<String> indexkeys, String key, ArrayList<String> indexraw) {
        long tm = this.ctime();
        String sg = this.sign(String.format("%s%d%s", this.authKey, tm, key));

        PutIndexReq req = new PutIndexReq();
        req.setIndexkey(indexkeys);
        req.setKey(key);
        req.setIndexraw(indexraw);
        req.setTime(tm);
        req.setSg(sg);

        ApiRsp rsp = new ApiRsp();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String raw = mapper.writeValueAsString(req);

            logger.info("create index #" + raw);

            rsp = this.post("/wdb/api/index/create", raw);
        } catch (JsonProcessingException e) {
            logger.error(String.format("%s", e));
            rsp.setCode(400);
            rsp.setMsg("json encode err");
        }

        return rsp;
    }

    public ApiRsp UpdateIndex(ArrayList<String> oindexkeys, ArrayList<String> cindexkeys, String key, ArrayList<String> indexraw) {
        long tm = this.ctime();
        String sg = this.sign(String.format("%s%d%s", this.authKey, tm, key));

        UpdateIndexReq req = new UpdateIndexReq();
        req.setOindexkey(oindexkeys);
        req.setCindexkey(cindexkeys);
        req.setKey(key);
        req.setIndexraw(indexraw);
        req.setTime(tm);
        req.setSg(sg);

        ApiRsp rsp = new ApiRsp();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String raw = mapper.writeValueAsString(req);

            logger.info("create index #" + raw);

            rsp = this.post("/wdb/api/index/update", raw);
        } catch (JsonProcessingException e) {
            logger.error(String.format("%s", e));
            rsp.setCode(400);
            rsp.setMsg("json encode err");
        }

        return rsp;
    }

    public ApiRsp DelIndex(ArrayList<String> indexkeys, String key) {
        long tm = this.ctime();
        String sg = this.sign(String.format("%s%d%s", this.authKey, tm, key));

        DelIndexReq req = new DelIndexReq();
        req.setIndexkey(indexkeys);
        req.setKey(key);
        req.setTime(tm);
        req.setSg(sg);

        ApiRsp rsp = new ApiRsp();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String raw = mapper.writeValueAsString(req);

            logger.info("create index #" + raw);

            rsp = this.post("/wdb/api/index/del", raw);
        } catch (JsonProcessingException e) {
            logger.error(String.format("%s", e));
            rsp.setCode(400);
            rsp.setMsg("json encode err");
        }

        return rsp;
    }

    public ListRsp ListIndex(String indexkey, String condition, long offset, long limit, String order) {
        long tm = this.ctime();
        String sg = this.sign(String.format("%s%s%d", this.authKey, indexkey, tm));

        ListIndexReq req = new ListIndexReq();
        req.setIndexkey(indexkey);
        req.setCondition(condition);
        req.setOffset(offset);
        req.setLimit(limit);
        req.setOrder(order);
        req.setTime(tm);
        req.setSg(sg);

        ListRsp listRsp = new ListRsp();
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            String raw = mapper.writeValueAsString(req);

            ApiRsp apiRsp = this.post("/wdb/api/index/list", raw);
            if (apiRsp.getCode() == 200) {
                ObjectMapper dmapper = new ObjectMapper();
                ListData list_data = dmapper.readValue(apiRsp.getData(), ListData.class);

                listRsp.setCode(200);
                listRsp.setTotal(list_data.getTotal());
                listRsp.setList(list_data.getList());
            } else {
                listRsp.setCode(400);
                listRsp.setMsg(apiRsp.getMsg());
            }
        } catch (Exception e) {
            logger.error(String.format("%s", e));
            listRsp.setCode(400);
            listRsp.setMsg("ListData encode");
        }
        

        return listRsp;
    }

    public ApiRsp CreateRawData(String key, byte[] data, ArrayList<String> categories) {
        String ckey = Optional.ofNullable(key).orElse("");
        ArrayList<String> ccategories = Optional.ofNullable(categories).orElse(new ArrayList<>());

        String content = Base64.encodeBase64String(data);
        long tm = this.ctime();
        String sg = this.sign(String.format("%s%d%s%s", this.authKey, tm, ckey, content));

        PutDataReq req = new PutDataReq();
        req.setKey(ckey);
        req.setCategories(ccategories);
        req.setContent(content);
        req.setTime(this.ctime());
        req.setSg(sg);

        ApiRsp rsp = new ApiRsp();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String raw = mapper.writeValueAsString(req);

            rsp = this.post("/wdb/api/create_raw", raw);
        } catch (JsonProcessingException e) {
            logger.error(String.format("%s", e));
            rsp.setCode(400);
            rsp.setMsg("CreateRawData json encode err");
        }

        return rsp;
    }

    public RawRsp GetRawData(String key) {
        String ckey = Optional.ofNullable(key).orElse("");

        long tm = this.ctime();
        String sg = this.sign(String.format("%s%d%s", this.authKey, tm, ckey));
        ApiRsp rsp = this.get(String.format("/wdb/api/get_raw?key=%s&time=%d&sg=%s", ckey, tm, sg));
        RawRsp rawRsp = new RawRsp();
        if (rsp.getCode() != 200) {
            rawRsp.setCode(500);
            rawRsp.setMsg(rsp.getMsg());
            return rawRsp;
        } else {
            byte[] raw = Base64.decodeBase64(rsp.getData());
            rawRsp.setCode(200);
            rawRsp.setMsg("");
            rawRsp.setSize(raw.length);
            rawRsp.setRaw(raw);
        }
        
        return rawRsp;
    }

    public RawRsp GetRangeData(String key, long offset, long limit) {
        String ckey = Optional.ofNullable(key).orElse("");

        long tm = this.ctime();
        String sg = this.sign(String.format("%s%d%s", this.authKey, tm, ckey));
        ApiRsp rsp = this.get(String.format("/wdb/api/get_range?key=%s&offset=%d&limit=%d&time=%d&sg=%s", ckey,
                offset, limit, tm, sg));
        RawRsp raw_rsp = new RawRsp();
        if (rsp.getCode() != 200) {
            raw_rsp.setCode(400);
            raw_rsp.setMsg(rsp.getMsg());
        } else {
            ObjectMapper mapper = new ObjectMapper();
            try {
                RangeData rg_data = mapper.readValue(rsp.getData(), RangeData.class);
                byte[] raw = Base64.decodeBase64(rg_data.getData());
                raw_rsp.setCode(200);
                raw_rsp.setSize(rg_data.getAll_size());
                raw_rsp.setRaw(raw);
            } catch (Exception e) {
                e.printStackTrace();
                raw_rsp.setCode(400);
                raw_rsp.setMsg("RangeData json decode err");
            }
        }

        return raw_rsp;
    }

    public long ctime() {
        return System.currentTimeMillis() / 1000;
    }

    public String sign(String data) {
        return DigestUtils.sha256Hex(data);
    }

    public ApiRsp get(String path) {
        String url = String.format("%s%s", this.host, path);
        logger.info("get url #" + url);

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse res = client.execute(get);
            String boby = EntityUtils.toString(res.getEntity());
            //logger.info("get bc #" + boby);

            if (res.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
                ObjectMapper mapper = new ObjectMapper();
                ApiRsp rsp = mapper.readValue(boby, ApiRsp.class);
                return rsp;
            } else {
                ApiRsp rsp = new ApiRsp();
                rsp.setCode(400);
                rsp.setMsg(boby);
                return rsp;
            }
        } catch (Exception e) {
            String msg = String.format("%s", e);
            ApiRsp rsp = new ApiRsp();
            rsp.setCode(400);
            rsp.setMsg(msg);
            return rsp;
        }
    }

    public ApiRsp post(String path, String data) {
        String url = String.format("%s%s", this.host, path);
        logger.info("post url #" + url + "---->" + data);

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        try {
            StringEntity raw = new StringEntity(data, ContentType.APPLICATION_JSON);
            raw.setContentEncoding("UTF-8");
            raw.setContentType("application/json");
            post.setEntity(raw);

            HttpResponse res = client.execute(post);
            String boby = EntityUtils.toString(res.getEntity());
            logger.info("post bc #" + boby);

            if (res.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
                ObjectMapper mapper = new ObjectMapper();
                ApiRsp rsp = mapper.readValue(boby, ApiRsp.class);
                return rsp;
            } else {
                ApiRsp rsp = new ApiRsp();
                rsp.setCode(400);
                rsp.setMsg(boby);
                return rsp;
            }
        } catch (Exception e) {
            String msg = String.format("%s", e);
            ApiRsp rsp = new ApiRsp();
            rsp.setCode(400);
            rsp.setMsg(msg);
            return rsp;
        }
    }
}
