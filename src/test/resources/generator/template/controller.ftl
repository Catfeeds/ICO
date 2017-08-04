package ${basePackage}.web;
import ${basePackage}.core.Result;
import ${basePackage}.model.${modelNameUpperCamel};
import ${basePackage}.service.${modelNameUpperCamel}Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by ${author} on ${date}.
*/
@RestController
@RequestMapping("${baseRequestMapping}")
public class ${modelNameUpperCamel}Controller {
    @Autowired
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @PostMapping("/add")
    public Result add(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.save(${modelNameLowerCamel});
        return Result.successResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        ${modelNameLowerCamel}Service.deleteById(id);
        return Result.successResult();
    }

    @PostMapping("/update")
    public Result update(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.update(${modelNameLowerCamel});
        return Result.successResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        ${modelNameUpperCamel} ${modelNameLowerCamel} = ${modelNameLowerCamel}Service.findById(id);
        return Result.successResult(${modelNameLowerCamel});
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return Result.successResult(pageInfo);
    }
}
