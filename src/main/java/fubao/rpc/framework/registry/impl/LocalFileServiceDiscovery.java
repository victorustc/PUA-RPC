package fubao.rpc.framework.registry.impl;

import fubao.rpc.framework.common.model.URL;
import fubao.rpc.framework.common.utils.LogUtils;
import fubao.rpc.framework.registry.ServiceDiscovery;
import fubao.rpc.framework.registry.load.balance.LoadBalanceStrategy;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalFileServiceDiscovery implements ServiceDiscovery {
    private static final String FILE_NAME_WITH_PATH = System.getProperty("user.dir") + File.separator + "tmp";
    private LoadBalanceStrategy loadBalanceStrategy;

    public LocalFileServiceDiscovery(LoadBalanceStrategy loadBalanceStrategy) {
        this.loadBalanceStrategy = loadBalanceStrategy;
    }

    @Override
    public URL discover(String serviceName) {
        try {
            Map<String, List<URL>> map = loadFile();
            List<URL> urls = map.get(serviceName);
            if (urls == null || urls.size() == 0) {
                return null;
            }
            URL url = loadBalanceStrategy.choose(urls);
            return url;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, List<URL>> loadFile() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = null;
        try {
//            InputStream in = this.getClass().getResourceAsStream(FILE_NAME_WITH_PATH);
            File file = new File(FILE_NAME_WITH_PATH);
            if (!file.exists()) {
                LogUtils.info(this,FILE_NAME_WITH_PATH+" not exist.");
                return new HashMap<>();
            }
            fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//            ObjectInputStream objectInputStream = new ObjectInputStream(in);
            return (Map<String, List<URL>>) objectInputStream.readObject();
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }
}
