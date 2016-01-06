package org.ligboy.android.utils;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.annotation.WorkerThread;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Network Util
 * @author Ligboy.Liu ligboy@gmail.com.
 */
public final class NetworkUtil {

    private NetworkUtil() {
        throw new IllegalAccessError();
    }

    /**
     * Check network connectivity.
     * <p>This method requires the caller to hold the permission
     * {@link android.Manifest.permission#ACCESS_NETWORK_STATE}.
     * @param context to use to check for network connectivity.
     * @return true if connected, false otherwise.
     */
    @RequiresPermission(allOf = {Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET})
    public static boolean isConnected(@NonNull Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * Returns the address of a host according to the given host string name host.
     * The host string may be either a machine name or a dotted string IP address.
     * If the latter, the hostName field is determined upon demand.
     * host can be null which means that an address of the loopback interface is returned.
     * @param host he hostName to be resolved to an address or null.
     * @return address of the host
     */
    @Nullable
    @WorkerThread
    @RequiresPermission(Manifest.permission.INTERNET)
    public static String resolveDomainName(@Nullable String host) {
        try {
            InetAddress inetAddress = InetAddress.getByName(host);
            return inetAddress.getHostAddress();
        } catch (UnknownHostException ignored) {
        }
        return null;
    }

    /**
     * Get local IPv4 internet address
     * @return Local IPv4 internet address
     */
    @Nullable
    public static String getLocalHostIpv4Address() {
        List<InetAddress> localInetAddress = getLocalHostAddress();
        for (InetAddress inetAddres : localInetAddress) {
            if (inetAddres instanceof Inet4Address) {
                return inetAddres.getHostAddress();
            }
        }
        return null;
    }

    /**
     * Get local IPv6 internet address
     * @return Local IPv6 internet address
     */
    @Nullable
    public static String getLocalHostIpv6Address() {
        List<InetAddress> localInetAddress = getLocalHostAddress();
        for (InetAddress inetAddres : localInetAddress) {
            if (inetAddres instanceof Inet6Address) {
                return inetAddres.getHostAddress();
            }
        }
        return null;
    }

    /**
     * Get local internet address
     * @return Local internet address
     */
    @NonNull
    public static List<InetAddress> getLocalHostAddress() {
        List<InetAddress> inetAddressList = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && !inetAddress.isAnyLocalAddress()) {
                        inetAddressList.add(inetAddress);
                    }
                }
            }
        } catch (SocketException ignored) {
        }
        return inetAddressList;
    }

    /**
     * Detects whether or not IPv6 is supported
     * @return true - IPv6 supported
     */
    public static boolean isIpv6Supported() {
        List<InetAddress> localInetAddress = getLocalHostAddress();
        for (InetAddress localInetAddres : localInetAddress) {
            if (localInetAddres instanceof Inet6Address) {
                return true;
            }
        }
        return false;
    }


}
