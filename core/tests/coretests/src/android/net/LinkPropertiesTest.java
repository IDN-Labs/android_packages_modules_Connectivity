/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.net;

import android.net.LinkProperties;
import android.net.RouteInfo;
import android.test.suitebuilder.annotation.SmallTest;
import junit.framework.TestCase;

import java.net.InetAddress;
import java.util.ArrayList;

public class LinkPropertiesTest extends TestCase {
    private static String ADDRV4 = "75.208.6.1";
    private static String ADDRV6 = "2001:0db8:85a3:0000:0000:8a2e:0370:7334";
    private static String DNS1 = "75.208.7.1";
    private static String DNS2 = "69.78.7.1";
    private static String GATEWAY1 = "75.208.8.1";
    private static String GATEWAY2 = "69.78.8.1";
    private static String NAME = "qmi0";

    @SmallTest
    public void testEqualsNull() {
        LinkProperties source = new LinkProperties();
        LinkProperties target = new LinkProperties();

        assertFalse(source == target);
        assertTrue(source.equals(target));
        assertTrue(source.hashCode() == target.hashCode());
    }

    @SmallTest
    public void testEqualsSameOrder() {
        try {
            LinkProperties source = new LinkProperties();
            source.setInterfaceName(NAME);
            // set 2 link addresses
            source.addLinkAddress(new LinkAddress(
                    NetworkUtils.numericToInetAddress(ADDRV4), 32));
            source.addLinkAddress(new LinkAddress(
                    NetworkUtils.numericToInetAddress(ADDRV6), 128));
            // set 2 dnses
            source.addDns(NetworkUtils.numericToInetAddress(DNS1));
            source.addDns(NetworkUtils.numericToInetAddress(DNS2));
            // set 2 gateways
            source.addRoute(new RouteInfo(NetworkUtils.numericToInetAddress(GATEWAY1)));
            source.addRoute(new RouteInfo(NetworkUtils.numericToInetAddress(GATEWAY2)));

            LinkProperties target = new LinkProperties();

            // All fields are same
            target.setInterfaceName(NAME);
            target.addLinkAddress(new LinkAddress(
                    NetworkUtils.numericToInetAddress(ADDRV4), 32));
            target.addLinkAddress(new LinkAddress(
                    NetworkUtils.numericToInetAddress(ADDRV6), 128));
            target.addDns(NetworkUtils.numericToInetAddress(DNS1));
            target.addDns(NetworkUtils.numericToInetAddress(DNS2));
            target.addRoute(new RouteInfo(NetworkUtils.numericToInetAddress(GATEWAY1)));
            target.addRoute(new RouteInfo(NetworkUtils.numericToInetAddress(GATEWAY2)));

            assertTrue(source.equals(target));
            assertTrue(source.hashCode() == target.hashCode());

            target.clear();
            // change Interface Name
            target.setInterfaceName("qmi1");
            target.addLinkAddress(new LinkAddress(
                    NetworkUtils.numericToInetAddress(ADDRV4), 32));
            target.addLinkAddress(new LinkAddress(
                    NetworkUtils.numericToInetAddress(ADDRV6), 128));
            target.addDns(NetworkUtils.numericToInetAddress(DNS1));
            target.addDns(NetworkUtils.numericToInetAddress(DNS2));
            target.addRoute(new RouteInfo(NetworkUtils.numericToInetAddress(GATEWAY1)));
            target.addRoute(new RouteInfo(NetworkUtils.numericToInetAddress(GATEWAY2)));
            assertFalse(source.equals(target));

            target.clear();
            target.setInterfaceName(NAME);
            // change link addresses
            target.addLinkAddress(new LinkAddress(
                    NetworkUtils.numericToInetAddress("75.208.6.2"), 32));
            target.addLinkAddress(new LinkAddress(
                    NetworkUtils.numericToInetAddress(ADDRV6), 128));
            target.addDns(NetworkUtils.numericToInetAddress(DNS1));
            target.addDns(NetworkUtils.numericToInetAddress(DNS2));
            target.addRoute(new RouteInfo(NetworkUtils.numericToInetAddress(GATEWAY1)));
            target.addRoute(new RouteInfo(NetworkUtils.numericToInetAddress(GATEWAY2)));
            assertFalse(source.equals(target));

            target.clear();
            target.setInterfaceName(NAME);
            target.addLinkAddress(new LinkAddress(
                    NetworkUtils.numericToInetAddress(ADDRV4), 32));
            target.addLinkAddress(new LinkAddress(
                    NetworkUtils.numericToInetAddress(ADDRV6), 128));
            // change dnses
            target.addDns(NetworkUtils.numericToInetAddress("75.208.7.2"));
            target.addDns(NetworkUtils.numericToInetAddress(DNS2));
            target.addRoute(new RouteInfo(NetworkUtils.numericToInetAddress(GATEWAY1)));
            target.addRoute(new RouteInfo(NetworkUtils.numericToInetAddress(GATEWAY2)));
            assertFalse(source.equals(target));

            target.clear();
            target.setInterfaceName(NAME);
            target.addLinkAddress(new LinkAddress(
                    NetworkUtils.numericToInetAddress(ADDRV4), 32));
            target.addLinkAddress(new LinkAddress(
                    NetworkUtils.numericToInetAddress(ADDRV6), 128));
            target.addDns(NetworkUtils.numericToInetAddress(DNS1));
            target.addDns(NetworkUtils.numericToInetAddress(DNS2));
            // change gateway
            target.addRoute(new RouteInfo(NetworkUtils.numericToInetAddress("75.208.8.2")));
            target.addRoute(new RouteInfo(NetworkUtils.numericToInetAddress(GATEWAY2)));
            assertFalse(source.equals(target));

        } catch (Exception e) {
            throw new RuntimeException(e.toString());
            //fail();
        }
    }

    @SmallTest
    public void testEqualsDifferentOrder() {
        try {
            LinkProperties source = new LinkProperties();
            source.setInterfaceName(NAME);
            // set 2 link addresses
            source.addLinkAddress(new LinkAddress(
                    NetworkUtils.numericToInetAddress(ADDRV4), 32));
            source.addLinkAddress(new LinkAddress(
                    NetworkUtils.numericToInetAddress(ADDRV6), 128));
            // set 2 dnses
            source.addDns(NetworkUtils.numericToInetAddress(DNS1));
            source.addDns(NetworkUtils.numericToInetAddress(DNS2));
            // set 2 gateways
            source.addRoute(new RouteInfo(NetworkUtils.numericToInetAddress(GATEWAY1)));
            source.addRoute(new RouteInfo(NetworkUtils.numericToInetAddress(GATEWAY2)));

            LinkProperties target = new LinkProperties();
            // Exchange order
            target.setInterfaceName(NAME);
            target.addLinkAddress(new LinkAddress(
                    NetworkUtils.numericToInetAddress(ADDRV6), 128));
            target.addLinkAddress(new LinkAddress(
                    NetworkUtils.numericToInetAddress(ADDRV4), 32));
            target.addDns(NetworkUtils.numericToInetAddress(DNS2));
            target.addDns(NetworkUtils.numericToInetAddress(DNS1));
            target.addRoute(new RouteInfo(NetworkUtils.numericToInetAddress(GATEWAY2)));
            target.addRoute(new RouteInfo(NetworkUtils.numericToInetAddress(GATEWAY1)));

            assertTrue(source.equals(target));
            assertTrue(source.hashCode() == target.hashCode());
        } catch (Exception e) {
            fail();
        }
    }

    @SmallTest
    public void testEqualsDuplicated() {
        try {
            LinkProperties source = new LinkProperties();
            // set 3 link addresses, eg, [A, A, B]
            source.addLinkAddress(new LinkAddress(
                    NetworkUtils.numericToInetAddress(ADDRV4), 32));
            source.addLinkAddress(new LinkAddress(
                    NetworkUtils.numericToInetAddress(ADDRV4), 32));
            source.addLinkAddress(new LinkAddress(
                    NetworkUtils.numericToInetAddress(ADDRV6), 128));

            LinkProperties target = new LinkProperties();
            // set 3 link addresses, eg, [A, B, B]
            target.addLinkAddress(new LinkAddress(
                    NetworkUtils.numericToInetAddress(ADDRV4), 32));
            target.addLinkAddress(new LinkAddress(
                    NetworkUtils.numericToInetAddress(ADDRV6), 128));
            target.addLinkAddress(new LinkAddress(
                    NetworkUtils.numericToInetAddress(ADDRV6), 128));

            assertTrue(source.equals(target));
            assertTrue(source.hashCode() == target.hashCode());
        } catch (Exception e) {
            fail();
        }
    }

    private void assertAllRoutesHaveInterface(String iface, LinkProperties lp) {
        for (RouteInfo r : lp.getRoutes()) {
            assertEquals(iface, r.getInterface());
        }
    }

    @SmallTest
    public void testRouteInterfaces() {
        LinkAddress prefix = new LinkAddress(
            NetworkUtils.numericToInetAddress("2001:db8::"), 32);
        InetAddress address = NetworkUtils.numericToInetAddress(ADDRV6);

        // Add a route with no interface to a LinkProperties with no interface. No errors.
        LinkProperties lp = new LinkProperties();
        RouteInfo r = new RouteInfo(prefix, address, null);
        lp.addRoute(r);
        assertEquals(1, lp.getRoutes().size());
        assertAllRoutesHaveInterface(null, lp);

        // Add a route with an interface. Except an exception.
        r = new RouteInfo(prefix, address, "wlan0");
        try {
          lp.addRoute(r);
          fail("Adding wlan0 route to LP with no interface, expect exception");
        } catch (IllegalArgumentException expected) {}

        // Change the interface name. All the routes should change their interface name too.
        lp.setInterfaceName("rmnet0");
        assertAllRoutesHaveInterface("rmnet0", lp);

        // Now add a route with the wrong interface. This causes an exception too.
        try {
          lp.addRoute(r);
          fail("Adding wlan0 route to rmnet0 LP, expect exception");
        } catch (IllegalArgumentException expected) {}

        // If the interface name matches, the route is added.
        lp.setInterfaceName("wlan0");
        lp.addRoute(r);
        assertEquals(2, lp.getRoutes().size());
        assertAllRoutesHaveInterface("wlan0", lp);

        // Routes with null interfaces are converted to wlan0.
        r = RouteInfo.makeHostRoute(NetworkUtils.numericToInetAddress(ADDRV6), null);
        lp.addRoute(r);
        assertEquals(3, lp.getRoutes().size());
        assertAllRoutesHaveInterface("wlan0", lp);

        // Check comparisons work.
        LinkProperties lp2 = new LinkProperties(lp);
        assertAllRoutesHaveInterface("wlan0", lp);
        assertEquals(0, lp.compareRoutes(lp2).added.size());
        assertEquals(0, lp.compareRoutes(lp2).removed.size());

        lp2.setInterfaceName("p2p0");
        assertAllRoutesHaveInterface("p2p0", lp2);
        assertEquals(3, lp.compareRoutes(lp2).added.size());
        assertEquals(3, lp.compareRoutes(lp2).removed.size());
    }

    @SmallTest
    public void testStackedInterfaces() {
        LinkProperties rmnet0 = new LinkProperties();
        rmnet0.setInterfaceName("rmnet0");

        LinkProperties clat4 = new LinkProperties();
        clat4.setInterfaceName("clat4");

        assertEquals(0, rmnet0.getStackedLinks().size());
        rmnet0.addStackedLink(clat4);
        assertEquals(1, rmnet0.getStackedLinks().size());
        rmnet0.addStackedLink(clat4);
        assertEquals(1, rmnet0.getStackedLinks().size());
        assertEquals(0, clat4.getStackedLinks().size());

        // Modify an item in the returned collection to see what happens.
        for (LinkProperties link : rmnet0.getStackedLinks()) {
            if (link.getInterfaceName().equals("clat4")) {
               link.setInterfaceName("newname");
            }
        }
        for (LinkProperties link : rmnet0.getStackedLinks()) {
            assertFalse("newname".equals(link.getInterfaceName()));
        }
    }
}
