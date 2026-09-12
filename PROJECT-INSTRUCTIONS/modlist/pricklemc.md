# PrickleMC

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8114924be9fc7edbdcf3
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `prickle-neoforge-1.21.1-21.1.11.jar`, mod id `prickle`, runtime `21.1.11`; AttributeFix 21.1.3 presente como consumer confirmado
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, PrickleMC 21.1.11 e AttributeFix 21.1.3 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** PrickleMC
- **Arquivo JAR:** `prickle-neoforge-1.21.1-21.1.11.jar`
- **Versão 1.21.1:** 21.1.11
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca
- **Função:** Biblioteca de configuração/utilidades usada por outros mods, sem conteúdo de gameplay relevante por si só.
- **Dependências:** NeoForge 1.21.1. Consumer causal confirmado: AttributeFix 21.1.3 usa net.darkhax.pricklemc; Enchantment Descriptions 21.1.11 integra o stack atual.
- **Sobreposição:** Não intercambiável automaticamente com outras config libraries; consumers dependem de API/formato específicos.
- **Compatibilidade/Riscos:** Library/config sem gameplay próprio. Riscos: schema/API drift, config inválida/corrompida, client/server config confusion e update isolado contra consumers. Remoção quebra consumer confirmado.
- **Observações:** Runtime 21.1.11. Changelog da release adiciona segurança para configs inválidas/corrompidas; formato JSON suporta `value` e comentários por `//`.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge/documentação oficial PrickleMC 21.1.11 + consumer AttributeFix já auditado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/prickle
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — PrickleMC 21.1.11 reconstruído: JSON config format, corruption safety, AttributeFix consumer, client/server, lifecycle, riscos e testes; Dependência preservada.
- **Histórico da decisão:** 2026-09-10 — reclassificado de `Sem decisão` para `Dependência` após confirmação no dossiê/source auditado de AttributeFix 21.1.3 de uso de `net.darkhax.pricklemc` para config management; Enchantment Descriptions 21.1.11 também mantém Prickle 21.1.11 no stack físico.
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `prickle-neoforge-1.21.1-21.1.11.jar`, mod id `prickle`, versão `21.1.11`, NeoForge 1.21.1. PrickleMC é library de configuração/utilidades baseada em JSON. **AttributeFix 21.1.3 é consumer causal confirmado**, portanto a decisão permanece **Dependência**.

## 1. Identidade e papel
- **Mod:** PrickleMC.
- **JAR:** `prickle-neoforge-1.21.1-21.1.11.jar`.
- **Mod id:** `prickle`.
- **Runtime:** `21.1.11`.
- **Ambiente:** Client & Server.
- **Licença:** LGPLv2.1.
- **Papel:** config/utilities compartilhados para consumers Darkhax e outros.
- **Decisão:** Dependência.

## 2. Consumer causal
AttributeFix 21.1.3 usa `net.darkhax.pricklemc` para config management; Enchantment Descriptions 21.1.11 também integra o mesmo stack. Remover Prickle isoladamente pode impedir bootstrap/configuração desses consumers.

## 3. Formato JSON de configuração
A documentação descreve formato JSON em que valores são encapsulados por objetos com `value`, permitindo metadata/comentários ao redor do valor funcional.

Prickle é owner do parser/infraestrutura; cada consumer continua owner da semântica de suas opções.

## 4. Comentários
O formato suporta comentários por chave `//`, com string ou array de strings. Comentários não devem alterar o valor parseado.

Testar round-trip de config para garantir que leitura/escrita não corrompa campos ou comentários.

## 5. Release 21.1.11
A build instalada é Release NeoForge 1.21.1. O changelog registra **proteções adicionais para configs inválidas ou corrompidas**.

Regression gate: arquivo malformado deve falhar de modo controlado/recoverable conforme implementação, sem transformar corrupção em valor silenciosamente incorreto.

## 6. Client/server
Configs podem existir em lados distintos conforme consumer. Não presumir que uma opção client-side controla state server-side.

Dedicated server precisa carregar Prickle e consumers sem classes de UI client-only em common path.

## 7. Lifecycle
Cobrir cold boot, config existente, config ausente, arquivo truncado/malformado, mudança de valor, restart e eventual reload suportado pelo consumer.

A library não deve duplicar handlers/listeners a cada reload.

## 8. Sobreposição
Prickle não é substituível por Cloth Config/Fzzy Config/ConfigLib apenas por função semelhante. Consumers dependem da API e formato específicos.

## 9. Riscos
1. Remoção quebra consumers confirmados.
2. Config schema drift entre versões.
3. Arquivo corrompido/malformado.
4. Client/server config confundida.
5. Update isolado da library contra consumer antigo.
6. Attribution error: config do consumer culpada como bug de Prickle sem isolamento.

## 10. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Prickle 21.1.11.
- [ ] AttributeFix carrega config sem missing class/API.
- [ ] Enchantment Descriptions inicia com stack atual.
- [ ] Config válida round-trip preserva valores.
- [ ] Comentários `//` não alteram parsing.
- [ ] Arquivo malformado não causa corrupção silenciosa de state.
- [ ] Restart preserva valores esperados.
- [ ] Update/reload não duplica listeners/config objects.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 11. Evidências e limites
- Modlist física: JAR, mod id/runtime e mixins common/NeoForge.
- CurseForge oficial: Release 21.1.11 NeoForge 1.21.1.
- Projeto/documentação: config JSON com `value` e comentários `//`; 21.1.11 adiciona safety para configs inválidas/corrompidas.
- Catálogo/source: AttributeFix 21.1.3 consumer confirmado.
- **Limite:** configs locais concretas dos consumers não foram abertas neste lote.
