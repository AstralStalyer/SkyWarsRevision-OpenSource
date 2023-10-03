package cn.rmc.skywars.util;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Objects;


public class LuckPerms {
    public static String getPrefix(Player p) {
        return "§7";
        /*
        if (LuckPermsProvider.get().getUserManager().getUser(p.getUniqueId()) == null||LuckPermsProvider.get().getUserManager().getUser(p.getUniqueId()).getCachedData().getMetaData(QueryOptions.defaultContextualOptions()).getPrefix() == null) {
            return "§7";
        } else {
            return ChatColor.translateAlternateColorCodes('&',LuckPermsProvider.get().getUserManager().getUser(p.getUniqueId()).getCachedData().getMetaData(QueryOptions.defaultContextualOptions()).getPrefix()+" ");
        }

         */
    }

    public static String getSuffix(Player p) {
        if (LuckPermsProvider.get().getUserManager().getUser(p.getUniqueId()).getCachedData().getMetaData(QueryOptions.defaultContextualOptions()).getSuffix() == null) {
            return "";
        } else {
            return ChatColor.translateAlternateColorCodes('&'," "+LuckPermsProvider.get().getUserManager().getUser(p.getUniqueId()).getCachedData().getMetaData(QueryOptions.defaultContextualOptions()).getSuffix());
        }
    }
    public static String getRank(Player p) {

        return LuckPermsProvider.get().getUserManager().getUser(p.getUniqueId()).getNodes().stream()
                .filter(NodeType.INHERITANCE::matches)
                .map(NodeType.INHERITANCE::cast)
                .filter(n -> n.getContexts().isSatisfiedBy(QueryOptions.defaultContextualOptions().context()))
                .map(InheritanceNode::getGroupName)
                .map(n -> LuckPermsProvider.get().getGroupManager().getGroup(n))
                .filter(Objects::nonNull)
                .min((o1, o2) -> {
                    int ret = Integer.compare(o1.getWeight().orElse(0), o2.getWeight().orElse(0));
                    return ret == 1 ? -1 : 1;
                })
                .map(Group::getName)
                .map(groupName -> convertGroupDisplayName(groupName))
                .orElse("");
    }
    public static String getExpire(Player p, String g){
        return LuckPermsProvider.get().getUserManager().getUser(p.getUniqueId()).getNodes().stream()
                .filter(Node::hasExpiry)
                .filter(n -> !n.hasExpired())
                .filter(NodeType.INHERITANCE::matches)
                .map(NodeType.INHERITANCE::cast)
                .filter(n -> n.getGroupName().equalsIgnoreCase(g))
                .filter(n -> n.getContexts().isSatisfiedBy(QueryOptions.defaultContextualOptions().context()))
                .map(Node::getExpiry)
                .map(Instant::getEpochSecond)
                .findFirst()
                .map(e -> String.valueOf(e*1000 - System.currentTimeMillis())/*TimeUtil.getTime((int) (e - currentTime))*/)
                .orElse("");
    }
    public static String getExpireindate(Player p, String g){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String get = dateFormat.format(Integer.valueOf(getExpire(p,g))+System.currentTimeMillis());
        return get;
    }
    public static String getExpireinCount(Player p, String g){
        return TimeUtils.getTime(Integer.valueOf(getExpire(p,g))/1000);
    }
    private static String convertGroupDisplayName(String groupName) {
        Group group = LuckPermsProvider.get().getGroupManager().getGroup(groupName);
        if (group != null) {
            groupName = group.getFriendlyName();
        }
        return groupName;
    }
    public static String permisi(Player p, String permission){
        return LuckPermsProvider.get().getUserManager().getUser(p.getUniqueId()).getNodes().stream()
                .filter(Node::hasExpiry)
                .filter(n -> n.getKey().equals(permission))
                .filter(n -> n.getContexts().isSatisfiedBy(QueryOptions.defaultContextualOptions().context()))
                .map(Node::getExpiryDuration)
                .filter(Objects::nonNull)
                .filter(d -> !d.isNegative())
                .findFirst()
                .map(d -> formatTime((int) d.getSeconds()))
                .orElse("");
    }
    public static String formatTime(int i){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/MM/dd hh:mm:ss");
        return simpleDateFormat.format(System.currentTimeMillis() + i*1000);
    }

}
