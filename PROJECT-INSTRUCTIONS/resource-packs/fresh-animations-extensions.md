# Fresh Animations: Extensions

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81c8a0a4e0dc4538ecdb
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `FA+All_Extensions-v1.8.1.zip`
- **Versão 1.21.1:** 1.8.1
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `FA+All_Extensions-v1.8.1.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A modlist física acessível de 08/09/2026 confirma `entity_model_features-3.3.5-1.21-neoforge.jar` (mod id `entity_model_features`, runtime `3.3.5`) e `entity_texture_features-7.2.1-1.21-neoforge.jar` (mod id `entity_texture_features`, runtime `7.2.1`).
- Fresh Animations `1.10.4` e o próprio bundle de Extensions são autoridades visuais registradas pela captura de Resource Packs, não por JAR top-level da modlist.
- A release 1.8.1 agrega seis extensões oficiais; a exportação não transforma essa lista em autoridade funcional sobre entidades, itens, AI ou gameplay.

## Propriedades do banco

- **Mod:** Fresh Animations: Extensions
- **Arquivo JAR:** `FA+All_Extensions-v1.8.1.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 1.8.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual
- **Função:** Bundle oficial de extensões para Fresh Animations que agrega módulos visuais de objects, quivers, emissive effects, creeper edit, spiders edit e details em um único resource pack.
- **Dependências:** Ecossistema Fresh Animations. O perfil contém Fresh Animations 1.10.4, EMF 3.3.5 e ETF 7.2.1; esses componentes formam o stack visual no qual as extensões são usadas.
- **Sobreposição:** Pode disputar creeper/spider/entity details e outros assets com packs dedicados. Creepers Refreshed + Fresh Animations, Mobs Refreshed + Fresh Animations e outras camadas específicas devem ter prioridade deliberada conforme o visual desejado.
- **Compatibilidade/Riscos:** All Extensions 1.8.1 suporta explicitamente Minecraft 1.21.1. Como o bundle toca múltiplas famílias de assets, pode competir com packs dedicados de creepers/spiders/objects/emissives e com compats Refreshed. Prioridade precisa ser validada asset por asset.
- **Observações:** Arquivo físico `FA+All_Extensions-v1.8.1.zip`, versão 1.8.1. Inclui Objects 2.1, Quivers 2.2, Emissive 1.5, Creeper Edit 2.1, Spiders Edit 2.1 e Details 2.2.1; changelog corrige villagers não dormindo em algumas versões.
- **Procedência:** CurseForge oficial Fresh Animations: Extensions, `FA+All_Extensions-v1.8.1.zip` + captura Resource Packs do perfil em 08/09/2026 + stack físico Fresh Animations/EMF/ETF.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/fresh-animations-extensions/files/7953813
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Fresh Animations Extensions 1.8.1, bundle oficial de seis extensões, suporte 1.21.1, stack FA/EMF/ETF, overlaps, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `FA+All_Extensions-v1.8.1.zip`, versão `1.8.1`. O arquivo oficial inclui Minecraft `1.21.1` entre as versões suportadas.

## 1. Papel e authority
Fresh Animations: Extensions é um bundle oficial de extensões visuais do ecossistema Fresh Animations. Ele amplia presentation/model resources; Minecraft continua authority de entidades, itens, AI, stats e gameplay.

## 2. Conteúdo confirmado da 1.8.1
A release agrega **Objects Add-on 2.1**, **Quivers Add-on 2.2**, **Emissive Add-on 1.5**, **Creeper Edit 2.1**, **Spiders Edit 2.1** e **Details Add-on 2.2.1**. O changelog também registra correção para villagers que não dormiam em algumas versões.

## 3. Stack visual físico
O perfil contém Fresh Animations `1.10.4`, EMF `3.3.5` e ETF `7.2.1`. As extensões são uma camada adicional sobre esse stack, não um sistema independente de gameplay.

## 4. Load order
Como o bundle cobre várias famílias de assets, ele deve ser ordenado conscientemente em relação a packs dedicados. Em paths coincidentes, maior prioridade vence; compats específicos podem precisar ficar acima do bundle.

## 5. Sobreposição
Creeper Edit pode disputar assets com Creepers Refreshed + Fresh Animations; Details/Spiders/Objects/Emissive podem competir com outros resource packs que alterem as mesmas entidades/objetos. A existência do bundle não significa que toda extensão tenha precedência desejada.

## 6. Client e reload
É conteúdo visual client-side. Resource reload/relog deve reconstruir os assets afetados sem alterar AI, items, entity state ou dados persistentes.

## 7. Riscos
1. Extensão genérica sobrescrever compat dedicado.
2. Duas extensões tocarem subconjuntos coincidentes.
3. EMF/ETF cache manter model/texture stale.
4. Mistura parcial de estilos por load order.
5. Update de Fresh Animations mudar assumptions internas do bundle.

## 8. Matriz de testes
- [ ] Objects e Quivers em situações representativas.
- [ ] Emissive effects em iluminação clara/escura.
- [ ] Creepers com o compat Refreshed ativo.
- [ ] Spiders e detalhes gerais de entidades.
- [ ] Villagers dormindo normalmente.
- [ ] Resource reload/relog sem model/texture quebrado.
- [ ] Confirmar que pack on/off não altera gameplay.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
CurseForge oficial confirma `FA+All_Extensions-v1.8.1.zip`, suporte a 1.21.1, os seis módulos incluídos e a correção de villagers. O catálogo não extrapola cobertura além desses recursos publicados.

> Boundary canônico: **as Extensions controlam somente apresentação dos assets que incluem; Minecraft/Fresh Animations base e demais mods mantêm suas authorities funcionais**.
