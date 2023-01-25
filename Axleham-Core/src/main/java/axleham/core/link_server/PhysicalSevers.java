package axleham.core.link_server;

public enum PhysicalSevers {

    SERVER_1( "77.68.21.119");

    private String ip_address;

    PhysicalSevers(String ip_address) {

        this.ip_address = ip_address;

    }

    public String getIpAddress() {
        return ip_address;
    }
}
