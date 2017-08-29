package com.tongwii.ico.service.impl;

import com.tongwii.ico.exception.StorageException;
import com.tongwii.ico.exception.StorageFileNotFoundException;
import com.tongwii.ico.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * 文件接口实现类
 *
 * @author Zeral
 * @date 2017-08-04
 */
@Service
public class FileServiceImpl implements FileService {

    private final Path rootLocation;

    public FileServiceImpl(@Value("${storage.location}") String location) {
        this.rootLocation = Paths.get(location);
        init();
    }

    @Override
    public Path store(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + fileName.substring(fileName.lastIndexOf("."));
        try {
            if (file.isEmpty()) {
                throw new StorageException("上传空文件失败 " + fileName);
            }
            if (fileName.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "无法存储当前目录外的相对路径的文件"
                                + fileName);
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(newFileName),
                    StandardCopyOption.REPLACE_EXISTING);
            return this.rootLocation.resolve(newFileName);
        }
        catch (IOException e) {
            throw new StorageException("存储文件失败 " + fileName, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        }
        catch (IOException e) {
            throw new StorageException("读取存储文件失败", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "找不到文件: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("找不到文件: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            if(!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
        } catch (IOException e) {
            throw new StorageException("无法初始化文件目录", e);
        }
    }
}
