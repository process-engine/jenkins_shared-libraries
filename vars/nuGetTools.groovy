#!/usr/bin/env groovy

def isNuGetPackagePublished(Map parameters = [:]) {

  def nuGetFeedURL = parameters.nuGetFeedURL;
  def packageName = parameters.package.toLowerCase();
  def packageVersion = parameters.version.toLowerCase();

  def authHeader = [
    name: "X-NuGet-ApiKey",
    value: parameters.nuGetToken,
    maskValue: true,
  ];

  /*
   * We need to get the api index to determine the
   * PackageVersionDisplayMetadataUriTemplate.
   */

  def indexResponse = httpRequest(
    url: nuGetFeedURL,
    quiet: true,
    customHeaders: [
      authHeader,
    ],
  );

  def feedIndex = [:];
  if (indexResponse.status == 200) {
    feedIndex = readJSON text: indexResponse.content;
  } else {
    error "Got non 200 response from api: ${indexResponse}";
  }

  def packageMetadataResource = feedIndex.resources.find({
    it.get("@type") == "PackageVersionDisplayMetadataUriTemplate"
  });

  def packageMetadataURLTemplate = packageMetadataResource.get("@id");

  def packageMetadataURL = packageMetadataURLTemplate
    .replace("{id-lower}", packageName)
    .replace("{version-lower}", packageVersion);

  def packageMetadataResponse = httpRequest(
    url: packageMetadataURL,
    quiet: true,
    customHeaders: [
      authHeader,
    ],
    validResponseCodes: "200,404",
  );

  return packageMetadataResponse.status == 200;
}