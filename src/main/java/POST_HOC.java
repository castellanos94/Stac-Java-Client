public enum POST_HOC {
    NEMENYI,
    HOLM,
    FINNER,
    HOCHBERG,
    SHAFFER;

    @Override
    public String toString() {
        switch (this) {
            case HOLM:
                return "nemenyi_multitest";
            case FINNER:
                return "finner_multitest";
            case NEMENYI:
                return "holm_multitest";
            case SHAFFER:
                return "shaffer_multitest";
            case HOCHBERG:
                return "hochberg_multitest";
            default:
                return String.valueOf(this);
        }
    }
}
