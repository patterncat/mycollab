package com.esofthead.mycollab.module.report;

import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.NodeTypeManager;
import javax.jcr.nodetype.NodeTypeTemplate;
import javax.jcr.nodetype.PropertyDefinitionTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.extensions.jcr.JcrSessionFactory;

import com.esofthead.mycollab.module.ecm.ContentException;
import com.esofthead.mycollab.module.mail.MailContentSessionFactory;

/**
 * 
 * @author MyCollab Ltd.
 * @since 4.0
 * 
 */
public class ReportContentSessionFactory extends JcrSessionFactory {

	private static Logger log = LoggerFactory
			.getLogger(MailContentSessionFactory.class);

	private static final String MAIL_NAMESPACE = "report";

	@Override
	protected void registerNodeTypes() throws Exception {
		log.info("Register node types");
		final String[] jcrNamespaces = getSession().getWorkspace()
				.getNamespaceRegistry().getPrefixes();
		boolean createNamespace = true;
		for (int i = 0; i < jcrNamespaces.length; i++) {
			if (jcrNamespaces[i].equals(MAIL_NAMESPACE)) {
				createNamespace = false;
				log.debug("Jackrabbit OCM namespace exists.");
			}
		}
		if (createNamespace) {
			getSession()
					.getWorkspace()
					.getNamespaceRegistry()
					.registerNamespace(MAIL_NAMESPACE,
							"http://www.esofthead.com/report");
			log.debug("Successfully created Mycollab content namespace.");
		}
		if (getSession().getRootNode() == null) {
			throw new ContentException("Jcr session setup not successful.");
		}

		NodeTypeManager manager = (NodeTypeManager) getSession().getWorkspace()
				.getNodeTypeManager();
		manager.registerNodeType(createreportContentType(manager), true);
		manager.registerNodeType(createMailFolderType(manager), true);
	}

	private NodeTypeTemplate createreportContentType(
			NodeTypeManager manager) throws NoSuchNodeTypeException,
			RepositoryException {
		log.info("Register mycollab content type");
		NodeType hierachyNode = manager.getNodeType(NodeType.NT_HIERARCHY_NODE);
		// Create content node type
		NodeTypeTemplate contentTypeTemplate = manager
				.createNodeTypeTemplate(hierachyNode);

		contentTypeTemplate.setAbstract(false);
		contentTypeTemplate.setMixin(false);
		contentTypeTemplate.setName("report:content");
		contentTypeTemplate.setPrimaryItemName("content");
		contentTypeTemplate
				.setDeclaredSuperTypeNames(new String[] { NodeType.NT_HIERARCHY_NODE });
		contentTypeTemplate.setQueryable(true);
		contentTypeTemplate.setOrderableChildNodes(false);
		log.debug("PROPERTY {} {}",
				contentTypeTemplate.getDeclaredPropertyDefinitions().length,
				contentTypeTemplate.getDeclaredChildNodeDefinitions().length);

		PropertyDefinitionTemplate createdUserPropertyTemplate = manager
				.createPropertyDefinitionTemplate();
		createdUserPropertyTemplate.setMultiple(false);
		createdUserPropertyTemplate.setName("report:createdUser");
		createdUserPropertyTemplate.setMandatory(true);
		createdUserPropertyTemplate.setRequiredType(PropertyType.STRING);
		contentTypeTemplate.getPropertyDefinitionTemplates().add(
				createdUserPropertyTemplate);

		PropertyDefinitionTemplate subjectPropertyTemplate = manager
				.createPropertyDefinitionTemplate();
		subjectPropertyTemplate.setMultiple(false);
		subjectPropertyTemplate.setName("report:subject");
		subjectPropertyTemplate.setMandatory(true);
		subjectPropertyTemplate.setRequiredType(PropertyType.STRING);
		contentTypeTemplate.getPropertyDefinitionTemplates().add(
				subjectPropertyTemplate);

		PropertyDefinitionTemplate bodyPropertyTemplate = manager
				.createPropertyDefinitionTemplate();
		bodyPropertyTemplate.setMultiple(false);
		bodyPropertyTemplate.setName("report:body");
		bodyPropertyTemplate.setMandatory(true);
		bodyPropertyTemplate.setRequiredType(PropertyType.STRING);
		contentTypeTemplate.getPropertyDefinitionTemplates().add(
				bodyPropertyTemplate);

		PropertyDefinitionTemplate modulePropertyTemplate = manager
				.createPropertyDefinitionTemplate();
		modulePropertyTemplate.setMultiple(false);
		modulePropertyTemplate.setName("report:module");
		modulePropertyTemplate.setMandatory(true);
		modulePropertyTemplate.setRequiredType(PropertyType.STRING);
		contentTypeTemplate.getPropertyDefinitionTemplates().add(
				modulePropertyTemplate);

		return contentTypeTemplate;
	}

	private NodeTypeTemplate createMailFolderType(NodeTypeManager manager)
			throws NoSuchNodeTypeException, RepositoryException {
		// Create content node type
		NodeTypeTemplate contentTypeTemplate = manager.createNodeTypeTemplate();

		contentTypeTemplate.setAbstract(false);
		contentTypeTemplate.setMixin(false);
		contentTypeTemplate.setName("report:folder");
		contentTypeTemplate.setPrimaryItemName("folder");
		contentTypeTemplate
				.setDeclaredSuperTypeNames(new String[] { NodeType.NT_FOLDER });
		contentTypeTemplate.setQueryable(true);
		contentTypeTemplate.setOrderableChildNodes(false);

		PropertyDefinitionTemplate createdPropertyTemplate = manager
				.createPropertyDefinitionTemplate();
		createdPropertyTemplate.setMultiple(false);
		createdPropertyTemplate.setName("report:createdUser");
		createdPropertyTemplate.setMandatory(true);
		createdPropertyTemplate.setRequiredType(PropertyType.STRING);
		contentTypeTemplate.getPropertyDefinitionTemplates().add(
				createdPropertyTemplate);
		return contentTypeTemplate;
	}
}
