package android

import sbt._

trait ApkSigningConfig {
  def storeType = "jks"
  def keyPass = Option.empty[String]
  def keystore: File
  def alias: String
  def storePass: String
}
case class PlainSigningConfig(override val keystore: File,
                              override val storePass: String,
                              override val alias: String,
                              override val keyPass: Option[String] = None,
                              override val storeType: String = "jks") extends ApkSigningConfig

case class PromptStorepassSigningConfig(override val keystore: File,
                                        override val alias: String,
                                        override val storeType: String = "jks") extends ApkSigningConfig {
  override def storePass =
    System.console.readPassword("Enter keystore password: ") mkString
}

case class PromptPasswordsSigningConfig(override val keystore: File,
                                        override val alias: String,
                                        override val storeType: String = "jks") extends ApkSigningConfig {
  import System.console
  override def storePass =
    console.readPassword("Enter keystore password: ") mkString
  override def keyPass =
    Option(console.readPassword("Enter password for key '%s': " format alias) mkString)
}
