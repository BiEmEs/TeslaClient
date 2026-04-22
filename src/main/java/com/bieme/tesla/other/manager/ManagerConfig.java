package com.bieme.tesla.other.manager;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.hacks.Module;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerConfig {

    public static ArrayList<String> configs = new ArrayList<>();
    private static Path configDir;

    public ManagerConfig() {
        try {
            configDir = Paths.get("config", "teslaclient");
            if (!Files.exists(configDir)) {
                Files.createDirectories(configDir);
            }
            loadConfigList();
        } catch (Exception e) {
        }
    }

    private void loadConfigList() {
        configs.clear();
        try {
            Files.list(configDir).forEach(path -> {
                if (path.toString().endsWith(".json")) {
                    configs.add(path.getFileName().toString().replace(".json", ""));
                }
            });
        } catch (Exception e) {
        }
    }

    public void saveConfig(String name) {
        try {
            Path file = configDir.resolve(name + ".json");
            StringBuilder sb = new StringBuilder();
            sb.append("{\n");
            sb.append("  \"modules\": [\n");

            List<Module> modules = Client.getHackManager().getModules();
            for (int i = 0; i < modules.size(); i++) {
                Module m = modules.get(i);
                sb.append("    {\n");
                sb.append("      \"name\": \"").append(m.getName()).append("\",\n");
                sb.append("      \"enabled\": ").append(m.isEnabled()).append(",\n");
                sb.append("      \"keybind\": ").append(m.getBind()).append("\n");
                sb.append("    }");
                if (i < modules.size() - 1) sb.append(",");
                sb.append("\n");
            }

            sb.append("  ]\n");
            sb.append("}\n");

            Files.writeString(file, sb.toString());
            loadConfigList();
        } catch (Exception e) {
        }
    }

    public void loadConfig(String name) {
        try {
            Path file = configDir.resolve(name + ".json");
            if (!Files.exists(file)) {
                return;
            }

            String content = Files.readString(file);
            String[] lines = content.split("\n");

            String currentModule = null;
            boolean currentEnabled = false;
            int currentBind = 0;

            for (String line : lines) {
                line = line.trim();
                if (line.startsWith("\"name\":")) {
                    currentModule = line.split(":")[1].trim().replace("\"", "").replace(",", "");
                } else if (line.startsWith("\"enabled\":")) {
                    currentEnabled = line.split(":")[1].trim().replace(",", "").equals("true");
                } else if (line.startsWith("\"keybind\":")) {
                    currentBind = Integer.parseInt(line.split(":")[1].trim().replace(",", ""));
                } else if (line.equals("}") && currentModule != null) {
                    Module m = Client.getHackManager().get_module_with_name(currentModule);
                    if (m != null) {
                        m.setEnabled(currentEnabled);
                        m.setBind(currentBind);
                    }
                    currentModule = null;
                }
            }
        } catch (Exception e) {
        }
    }

    public ArrayList<String> getConfigs() {
        return configs;
    }

    public void save() {
    }

    public void load() {
    }

    public void saveSettings() {
    }
}