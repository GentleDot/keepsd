package net.gentledot.keepsd.controller;

import net.gentledot.keepsd.mapper.TestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TestMapper testMapper;

    @RequestMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/home")
    @ResponseBody
    public ResponseEntity<String> home() {
        log.info("home 호출 (ResponseBody)");
        return new ResponseEntity<>("welcome!", HttpStatus.OK);
    }

    @GetMapping("/now")
    @ResponseBody
    public ResponseEntity<String> now() {
        log.info("now 호출 (ResponseBody)");
        return new ResponseEntity<>(testMapper.current() + " / " + testMapper.getCount(), HttpStatus.OK);
    }

    @GetMapping("/save/{str}")
    @ResponseBody
    public ResponseEntity<String> saveAndGet(@PathVariable("str") String tmpstr) {
        log.info("now 호출 (ResponseBody)");
        return new ResponseEntity<>("저장 처리 수 : " + testMapper.saveTmp(tmpstr) + " / 저장 데이터 조회 : " + testMapper.getTmp(tmpstr), HttpStatus.OK);
    }


}