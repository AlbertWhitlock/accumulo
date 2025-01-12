/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.accumulo.minicluster;

import java.io.File;
import java.util.Map;

import org.apache.accumulo.miniclusterImpl.MiniAccumuloConfigImpl;

/**
 * Holds configuration for {@link MiniAccumuloCluster}. Required configurations must be passed to
 * constructor(s) and all other configurations are optional.
 *
 * @since 1.5.0
 */
public class MiniAccumuloConfig {

  private MiniAccumuloConfigImpl impl;

  MiniAccumuloConfig(MiniAccumuloConfigImpl config) {
    this.impl = config;
  }

  MiniAccumuloConfigImpl getImpl() {
    return impl;
  }

  /**
   * @param dir
   *          An empty or nonexistent directory that Accumulo and Zookeeper can store data in.
   *          Creating the directory is left to the user. Java 7, Guava, and Junit provide methods
   *          for creating temporary directories.
   * @param rootPassword
   *          The initial password for the Accumulo root user
   */
  public MiniAccumuloConfig(File dir, String rootPassword) {
    this.impl = new MiniAccumuloConfigImpl(dir, rootPassword);
  }

  /**
   * Calling this method is optional. If not set, it defaults to two.
   *
   * @param numTservers
   *          the number of tablet servers that mini accumulo cluster should start
   */
  public MiniAccumuloConfig setNumTservers(int numTservers) {
    impl.setNumTservers(numTservers);
    return this;
  }

  /**
   * Calling this method is optional. If not set, defaults to 'miniInstance'
   *
   * @since 1.6.0
   */
  public MiniAccumuloConfig setInstanceName(String instanceName) {
    impl.setInstanceName(instanceName);
    return this;
  }

  /**
   * Calling this method is optional. If not set, it defaults to an empty map.
   *
   * @param siteConfig
   *          key/values that you normally put in accumulo.properties can be put here.
   */
  public MiniAccumuloConfig setSiteConfig(Map<String,String> siteConfig) {
    impl.setSiteConfig(siteConfig);
    return this;
  }

  /**
   * Calling this method is optional. A random port is generated by default
   *
   * @param zooKeeperPort
   *          A valid (and unused) port to use for the zookeeper
   *
   * @since 1.6.0
   */
  public MiniAccumuloConfig setZooKeeperPort(int zooKeeperPort) {
    impl.setZooKeeperPort(zooKeeperPort);
    return this;
  }

  /**
   * Configure an existing ZooKeeper instance to use. Calling this method is optional. If not set, a
   * new ZooKeeper instance is created.
   *
   * @param existingZooKeepers
   *          Connection string for a already-running ZooKeeper instance. A null value will turn off
   *          this feature.
   *
   * @since 2.1.0
   */
  public MiniAccumuloConfig setExistingZooKeepers(String existingZooKeepers) {
    impl.setExistingZooKeepers(existingZooKeepers);
    return this;
  }

  /**
   * Configure the time to wait for ZooKeeper to startup. Calling this method is optional. The
   * default is 20000 milliseconds
   *
   * @param zooKeeperStartupTime
   *          Time to wait for ZooKeeper to startup, in milliseconds
   *
   * @since 1.6.1
   */
  public MiniAccumuloConfig setZooKeeperStartupTime(long zooKeeperStartupTime) {
    impl.setZooKeeperStartupTime(zooKeeperStartupTime);
    return this;
  }

  /**
   * Sets the amount of memory to use in the manager process. Calling this method is optional.
   * Default memory is 128M
   *
   * @param serverType
   *          the type of server to apply the memory settings
   * @param memory
   *          amount of memory to set
   *
   * @param memoryUnit
   *          the units for which to apply with the memory size
   *
   * @since 1.6.0
   */
  public MiniAccumuloConfig setMemory(ServerType serverType, long memory, MemoryUnit memoryUnit) {
    impl.setMemory(serverType, memory, memoryUnit);
    return this;
  }

  /**
   * Sets the default memory size to use. This value is also used when a ServerType has not been
   * configured explicitly. Calling this method is optional. Default memory is 128M
   *
   * @param memory
   *          amount of memory to set
   *
   * @param memoryUnit
   *          the units for which to apply with the memory size
   *
   * @since 1.6.0
   */
  public MiniAccumuloConfig setDefaultMemory(long memory, MemoryUnit memoryUnit) {
    impl.setDefaultMemory(memory, memoryUnit);
    return this;
  }

  /**
   * @return a copy of the site config
   */
  public Map<String,String> getSiteConfig() {
    return impl.getConfiguredSiteConfig();
  }

  /**
   * @return name of configured instance
   *
   * @since 1.6.0
   */
  public String getInstanceName() {
    return impl.getInstanceName();
  }

  /**
   * @return The configured zookeeper port
   *
   * @since 1.6.0
   */
  public int getZooKeeperPort() {
    return impl.getConfiguredZooKeeperPort();
  }

  /**
   * @param serverType
   *          get configuration for this server type
   *
   * @return memory configured in bytes, returns default if this server type is not configured
   *
   * @since 1.6.0
   */
  public long getMemory(ServerType serverType) {
    return impl.getMemory(serverType);
  }

  /**
   * @return memory configured in bytes
   *
   * @since 1.6.0
   */
  public long getDefaultMemory() {
    return impl.getDefaultMemory();
  }

  /**
   * @return the base directory of the cluster configuration
   */
  public File getDir() {
    return impl.getDir();
  }

  /**
   * @return the root password of this cluster configuration
   */
  public String getRootPassword() {
    return impl.getRootPassword();
  }

  /**
   * @return the number of tservers configured for this cluster
   */
  public int getNumTservers() {
    return impl.getNumTservers();
  }

  /**
   * @return is the current configuration in jdwpEnabled mode?
   *
   * @since 1.6.0
   */
  public boolean isJDWPEnabled() {
    return impl.isJDWPEnabled();
  }

  /**
   * @param jdwpEnabled
   *          should the processes run remote jdwpEnabled servers?
   * @return the current instance
   *
   * @since 1.6.0
   */
  public MiniAccumuloConfig setJDWPEnabled(boolean jdwpEnabled) {
    impl.setJDWPEnabled(jdwpEnabled);
    return this;
  }

  /**
   * @return the paths to use for loading native libraries
   *
   * @since 1.6.0
   */
  public String[] getNativeLibPaths() {
    return impl.getNativeLibPaths();
  }

  /**
   * Sets the path for processes to use for loading native libraries
   *
   * @param nativePathItems
   *          the nativePathItems to set
   *
   * @since 1.6.0
   */
  public MiniAccumuloConfig setNativeLibPaths(String... nativePathItems) {
    impl.setNativeLibPaths(nativePathItems);
    return this;
  }

  /**
   * Sets the classpath elements to use when spawning processes.
   *
   * @param classpathItems
   *          the classpathItems to set
   * @return the current instance
   * @since 2.0.0
   */
  public MiniAccumuloConfig setClasspath(String... classpathItems) {
    impl.setClasspathItems(classpathItems);
    return this;
  }
}
