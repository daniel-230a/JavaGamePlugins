package axleham.ranks.perms;

public enum PlayerPerms {

    PERMS_BLOCKBREAK("permission.blockbreak"),
    PERMS_BLOCKPLACE("permission.blockplace"),
    PERMS_EMPTYBUCKET("permission.emptyBucket"),
    PERMS_STAFF("permission.staff");

    private String perm_node;


    PlayerPerms(String permission_node) {

        this.perm_node = permission_node;


    }


    public String getPermNode() {

        return this.perm_node;

    }



}
